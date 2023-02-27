package com.example.demo.services;

import com.example.demo.constants.PostType;
import com.example.demo.dto.post.UpdatePostRequest;
import com.example.demo.dto.tag.UpdateTagRequest;
import com.example.demo.exception.PostException;
import com.example.demo.feign.FriendService;
import com.example.demo.feign.PersonService;
import com.example.demo.dto.post.CreatePostRequest;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.*;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostLikeRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.TagRepository;
import com.example.demo.repositories.specifications.PostSpecification;
import constants.NotificationType;
import dto.notification.ContentDTO;
import dto.postDto.PostDTO;
import dto.postDto.PostNotificationRequest;
import dto.userDto.PersonDTO;
import kafka.annotation.SubmitToKafka;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import security.TokenAuthentication;
import security.dto.TokenData;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PersonService personService;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;
    private final FriendService friendService;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    public Post findPostById(Long postId, Long personId) {
        return postRepository.findByIdAndAuthorId(postId, personId)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public PostDTO findById(Long id, String email) {
        PostDTO postDTO = postMapper.toDTO(findPostById(id));
        PersonDTO personDTO = personService.getPersonDTOByEmail(email);
        postDTO.setCommentAmount(commentRepository.getCommentsCount(id));
        postDTO.setLikeAmount(postLikeRepository.getLikesCount(id));
        postDTO.setMyLike(postLikeRepository.findByPostIdAndUserId(id, personDTO.getId()).isPresent());
        return postDTO;
    }

    public PostDTO editPost(UpdatePostRequest req, TokenData tokenData) {
        PersonDTO user = personService.getPersonDTOByEmail(tokenData.getEmail());
        Post post = postRepository.findByIdAndAuthorId(req.getPostId(), user.getId()).get();
        post.setTitle(req.getTitle());
        post.setPostText(req.getText());
        post.setIsBlocked(req.getIsBlocked());
        post.setWithFriends(req.getWithFriends());

        if (Optional.ofNullable(req.getUpdateTagsRequests()).isPresent()) {
            updateTags(req.getUpdateTagsRequests(), post);
        } else {
            post.setTags(new HashSet<>());
        }
        return postMapper.toDTO(postRepository.save(post));
    }

    public void delete(Long id) {
        Post post = findPostById(id);

        post.setIsDelete(true);
        post.setIsBlocked(true);

        postRepository.save(post);
    }

    public void createPost(CreatePostRequest req, TokenData tokenData) {
        ZonedDateTime time = ZonedDateTime.now();
        PostType postType = req.getPublishTime() == null || req.getPublishTime().isBefore(time) ?
                PostType.POSTED : PostType.SCHEDULED;
        ZonedDateTime publishTime = req.getPublishTime() == null ? time : req.getPublishTime();
        PersonDTO user = personService.getPersonDTOByEmail(tokenData.getEmail());
        Post post = Post.builder()
                .title(req.getTitle())
                .postText(req.getText())
                .authorId(user.getId())
                .time(time)
                .type(postType)
                .myLike(false)
                .publishTime(publishTime)
                .tags(getOrBuildTags(req.getTags()))
                .build();
        postRepository.save(post);
        createNotification(post);
    }

    public Page<PostDTO> getAllPostsByUser(String email, Pageable pageable) {
        return getAllPostsByUser(personService.getPersonDTOByEmail(email).getId(), pageable);
    }

    public Page<PostDTO> getAllPostsByUser(Long id, Pageable pageable) {
        ZonedDateTime time = ZonedDateTime.now();
        List<PostDTO> posts = postRepository.findAllByAuthorIdAndIsDeleteIsFalseAndPublishTimeBeforeOrderByPublishTimeDesc(id, time, pageable).get()
                .map(post -> {
                    if (post.getType() == PostType.SCHEDULED) {
                        setTypePosted(post);
                    }
                    PostDTO postDTO = postMapper.toDTO(post);
                    postDTO.setLikeAmount(postLikeRepository.getLikesCount(postDTO.getId()));
                    postDTO.setCommentAmount(commentRepository.getCommentsCount(postDTO.getId()));
                    postDTO.setMyLike(postLikeRepository.findByPostIdAndUserId(postDTO.getId(), id).isPresent());
                    return postDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(posts);
    }

    private Post setTypePosted(Post post) {
        post.setType(PostType.POSTED);
        postRepository.save(post);
        return post;
    }

    public Set<Long> findAllUsersBySubstringsInFirstOrLastNames(String authorSubstringsInNames) {
        String[] words = authorSubstringsInNames.split("\\s");
        Set<PersonDTO> personDTOSet = personService.searchAllBySubstringInFirstOrLastName(words[0]);
        for (int i = 1; i < words.length; i++) {
            personDTOSet.retainAll(personService.searchAllBySubstringInFirstOrLastName(words[i]));
        }
        return personDTOSet.stream().map(PersonDTO::getId).collect(Collectors.toSet());
    }

    public Page<PostDTO> findAllPosts(Boolean withFriends, ZonedDateTime toTime, ZonedDateTime fromTime,
                                      Boolean isDelete, List<String> tags, String range, String authorSubStringsInNames,
                                      Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page, offset, Sort.by("publishTime").descending());

        if (range != null) {
            switch (range) {
                case ("year"):
                    toTime = ZonedDateTime.now();
                    fromTime = ZonedDateTime.now().minusYears(1);
                    break;
                case ("month"):
                    toTime = ZonedDateTime.now();
                    fromTime = ZonedDateTime.now().minusMonths(1);
                    break;
                case ("week"):
                    toTime = ZonedDateTime.now();
                    fromTime = ZonedDateTime.now().minusWeeks(1);
                    break;
                case ("alltime"):
                    toTime = ZonedDateTime.now();
                    fromTime = LocalDateTime.of(1, 1, 1, 1, 1, 1, 1)
                            .atZone(ZoneId.of("UTC"));
                    break;
            }
        }

        List<PostDTO> posts = postRepository.findAllByFilter(withFriends,
                        isDelete, ZonedDateTime.now(), toTime, fromTime).stream()
                .map(post -> {
                    if (post.getType() == PostType.SCHEDULED) {
                        setTypePosted(post);
                    }
                    PostDTO postDTO = postMapper.toDTO(post);
                    postDTO.setCommentAmount(commentRepository.getCommentsCount(postDTO.getId()));
                    postDTO.setLikeAmount(postLikeRepository.getLikesCount(postDTO.getId()));
                    return postDTO;
                })
                .sorted(Comparator.comparing(PostDTO::getPublishTime).reversed())
                .collect(Collectors.toList());

        if (authorSubStringsInNames != null && !authorSubStringsInNames.isBlank()) {
            Set<Long> usersList = findAllUsersBySubstringsInFirstOrLastNames(authorSubStringsInNames);
            posts = posts.stream()
                    .filter(p -> usersList.contains(p.getAuthorId()))
                    .sorted(Comparator.comparing(PostDTO::getPublishTime).reversed())
                    .collect(Collectors.toList());
        }

        if (tags == null || tags.isEmpty()) {
            return new PageImpl<>(posts, PageRequest.of(page, offset), offset);
        }

        List<PostDTO> finalPosts = posts;
        return new PageImpl<>(tagRepository.findAllByTagIn(tags).stream().map(Tag::getPosts)
                    .flatMap(Collection::stream).distinct()
                    .map(postMapper::toDTO)
                    .filter(p -> finalPosts.stream().map(PostDTO::getId).collect(Collectors.toList()).contains(p.getId()))
                    .sorted(Comparator.comparing(PostDTO::getPublishTime).reversed())
                    .collect(Collectors.toList()),
                pageable, postRepository.countAllByTagsIsNotNull());
    }

    private Tag getOrBuildTag(String text) {
        return tagRepository.findByTagIgnoreCase(text)
                .orElseGet(() -> Tag.builder()
                        .tag(text)
                        .build());
    }

    private Set<Tag> getOrBuildTags(Set<String> tagRequests) {
        if (tagRequests == null) {
            return null;
        }
        return tagRequests.stream()
                .map(this::getOrBuildTag)
                .collect(Collectors.toSet());
    }

    public void updateTags(List<UpdateTagRequest> reqs, Post post) {
        post.setTags(getOrBuildTags(reqs.stream()
                .map(UpdateTagRequest::getText)
                .collect(Collectors.toSet())));
    }

    public void deleteTags(Long id, List<Long> tagIds, TokenData tokenData) {
        Post post = findPostById(id, tokenData.getId());
        post.setTags(post.getTags().stream()
                .filter(tag -> !tagIds.contains(tag.getId()))
                .collect(Collectors.toSet()));
        postRepository.save(post);
    }

    public List<PostDTO> getAllFriendsNews(Pageable pageable) {
        List<Long> friendsIds = friendService.getFriendId();
        return postRepository.findByAuthorIdInAndIsDeleteIsFalseOrderByPublishTimeDesc(friendsIds, pageable).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());

    }

    @SubmitToKafka(topic = "Post")
    public PostNotificationRequest createNotification(Post post){
        return PostNotificationRequest.builder()
                .authorId(post.getAuthorId())
                .title(post.getTitle())
                .type(NotificationType.POST)
                .content(ContentDTO.builder()
                        .text(post.getPostText())
                        .attaches(new ArrayList<>())
                        .build())
                .friendsId(friendService.getFriendId())
                .build();
    }

    public void likePost(Long postId, TokenAuthentication authentication){
        Post post = postRepository.findById(postId).get();
        PersonDTO personDTO = personService.getPersonDTOByEmail(authentication.getTokenData().getEmail());
        if (postLikeRepository.findByPostIdAndUserId(postId, personDTO.getId()).isEmpty()){
            if (post.getAuthorId() == personDTO.getId()){
                setMyLike(true, post);
            }
            newPostLike(personDTO, post);
        }
        else throw new PostException("Already liked", HttpStatus.BAD_REQUEST);
    }

    private void newPostLike(PersonDTO personDTO, Post post){
        PostLike postLike = new PostLike();
        postLike.setUserId(personDTO.getId());
        postLike.getPosts().add(post);
        postLikeRepository.save(postLike);
    }

    private void setMyLike(Boolean isMyLike, Post post){
        post.setMyLike(isMyLike);
        postRepository.save(post);
    }

    public void deleteLikeFromPost(Long postId, TokenAuthentication authentication){
        Post post = postRepository.findById(postId).get();
        PersonDTO personDTO = personService.getPersonDTOByEmail(authentication.getTokenData().getEmail());
        if (postLikeRepository.findByPostIdAndUserId(postId, personDTO.getId()).isPresent()) {
            if (post.getAuthorId() == personDTO.getId()) {
                post.setMyLike(false);
                postRepository.save(post);
            }
            PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, personDTO.getId()).get();
            postLikeRepository.delete(postLike);
        } else throw new PostException("Not liked", HttpStatus.BAD_REQUEST);
    }

    public List<PostDTO> getAllDelayedPosts(Long id, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page, offset, Sort.by("publishTime").descending());
        return postRepository.findAllByAuthorIdAndType( id, PostType.SCHEDULED, pageable).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getAllPosts(){
        return postRepository.findAll().stream().map(postMapper::toDTO).collect(Collectors.toList());
    }

    public Page<PostDTO> getAllPosts(String searchedTitle, Integer page){
        Specification<Post> spec = Specification.where(null);
        if (searchedTitle != null){
            spec = spec.and(PostSpecification.likeSearchedTitle(searchedTitle));
        }
        Page<Post> posts = postRepository.findAll(spec, PageRequest.of(page, 20));
        return new PageImpl<>(posts.stream().map(postMapper::toDTO).collect(Collectors.toList()));
    }

    public List<PostDTO> getAllPostsByTimeBetween(LocalDate date1, LocalDate date2){
        return postRepository.findAllPostsByTimeBetween(date1,date2)
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<PostDTO> getAllPostsByIsBlockedIsTrue(String searchedTitle,Integer page){
        if (searchedTitle!=null){
            Specification<Post> spec = Specification
                    .where(PostSpecification.likeSearchedTitle(searchedTitle))
                    .and(PostSpecification.isBlockedPost(true));
            Page<Post> posts = postRepository.findAll(spec,PageRequest.of(page,20));
            return new PageImpl<>(posts.stream()
                    .map(postMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return new PageImpl<>(postRepository.findAllPostsByIsBlockedIsTrue()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList()));
    }

    public Page<PostDTO> getAllActivePosts(String searchedTitle,Integer page){
        if (searchedTitle!=null){
            Specification<Post> spec = Specification
                    .where(PostSpecification.likeSearchedTitle(searchedTitle))
                    .and(PostSpecification.isBlockedPost(false))
                    .and(PostSpecification.isDeletePost(false));
            Page<Post> posts = postRepository.findAll(spec,PageRequest.of(page,20));
            return new PageImpl<>(posts.stream()
                    .map(postMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return new PageImpl<>(postRepository.findAllPostsByIsBlockedIsFalseAndIsDeleteIsFalse()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList()));
    }
}

