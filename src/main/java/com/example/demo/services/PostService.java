package com.example.demo.services;

import com.example.demo.constants.PostType;
import com.example.demo.dto.post.UpdatePostRequest;
import com.example.demo.dto.tag.UpdateTagRequest;
import com.example.demo.exception.PostException;
import com.example.demo.feign.FriendService;
import com.example.demo.feign.PersonService;
import com.example.demo.dto.post.CreatePostRequest;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.model.PostLike;
import com.example.demo.model.Tag;
import com.example.demo.repositories.PostLikeRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.TagRepository;
import com.example.demo.repositories.specifications.PostSpecification;
import dto.postDto.PostDTO;
import dto.userDto.PersonDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public Post findPostById(Long postId, Long personId) {
        return postRepository.findByIdAndAuthorId(postId, personId)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public PostDTO findById(Long id) {
        return postMapper.toDTO(findPostById(id));
    }

    public PostDTO editPost(UpdatePostRequest req, TokenData tokenData){
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

    public void delete(Long id){
        Post post = findPostById(id);

        post.setIsDelete(true);
        post.setIsBlocked(true);

        postRepository.save(post);
    }

    public void createPost(CreatePostRequest req, TokenData tokenData){
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        PostType postType = req.getPublishTime() == null || req.getPublishTime().isBefore(time)?
                PostType.POSTED : PostType.SCHEDULED;
        LocalDateTime publishTime = req.getPublishTime() == null ? time : req.getPublishTime();
        PersonDTO user = personService.getPersonDTOByEmail(tokenData.getEmail());
        Post post = Post.builder()
                .title(req.getTitle())
                .postText(req.getText())
                .authorId(user.getId())
                .time(time)
                .type(postType)
                .publishTime(publishTime)
                .tags(getOrBuildTags(req.getTags()))
                .build();
        postRepository.save(post);
    }

    public Page<PostDTO> getAllPostsByUser(String email, Pageable pageable){
        return getAllPostsByUser(personService.getPersonDTOByEmail(email).getId(), pageable);
    }

    public Page<PostDTO> getAllPostsByUser(Long id, Pageable pageable) {
        List<PostDTO> posts = postRepository.findAllByAuthorIdAndIsDeleteIsFalseAndPublishTimeBeforeOrderByTimeDesc(id,LocalDateTime.now(), pageable).get()
                .map(post -> {
                    if (post.getType() == PostType.SCHEDULED) {
                        setTypePosted(post);
                    }
                    PostDTO postDTO = postMapper.toDTO(post);
                    return postDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(posts);
    }

    private Post setTypePosted(Post post){
        post.setType(PostType.POSTED);
        postRepository.save(post);
        return post;
    }

    public Page<PostDTO> findAllPosts(Boolean withFriends, LocalDateTime toTime, LocalDateTime fromTime,
                                      Boolean isDelete, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page, offset, Sort.by("time").descending());

        List<PostDTO> posts = postRepository.findAllByFilter(withFriends,
                        isDelete, LocalDateTime.now(), toTime, fromTime, pageable).get()
                .map(post -> {
                    if (post.getType() == PostType.SCHEDULED) {
                        setTypePosted(post);
                    }
                    PostDTO postDTO = postMapper.toDTO(post);
                    return postDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(posts);
    }


    private Tag getOrBuildTag(String text) {
        return tagRepository.findByTagIgnoreCase(text)
                .orElseGet(() -> Tag.builder()
                        .tag(text)
                        .build());
    }

    private Set<Tag> getOrBuildTags(Set<String> tagRequests) {
        return tagRequests.stream()
                .map(this::getOrBuildTag)
                .collect(Collectors.toSet());
    }

    public void updateTags(List<UpdateTagRequest> reqs, Post post){
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

    public List<PostDTO> getAllFriendsNews(Pageable pageable){
        List<Long> friendsIds = friendService.getFriendId();
        return postRepository.findByAuthorIdInAndIsDeleteIsFalseOrderByTimeDesc(friendsIds, pageable).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());

    }

    public void likePost(Long postId, TokenAuthentication authentication){
        Post post = postRepository.findById(postId).get();
        PersonDTO personDTO = personService.getPersonDTOByEmail(authentication.getTokenData().getEmail());
        if (postLikeRepository.findByPostIdAndUserId(postId, personDTO.getId()).isEmpty()){
            post.setMyLike(post.getAuthorId() == personDTO.getId());
            postRepository.save(post);

            PostLike postLike = new PostLike();
            postLike.setUserId(personDTO.getId());
            postLike.getPosts().add(post);
            postLikeRepository.save(postLike);
        }
        else throw new PostException("Already liked", HttpStatus.BAD_REQUEST);

    }

    public void deleteLikeFromPost(Long postId, TokenAuthentication authentication){
        Post post = postRepository.findById(postId).get();
        PersonDTO personDTO = personService.getPersonDTOByEmail(authentication.getTokenData().getEmail());
        if (postLikeRepository.findByPostIdAndUserId(postId, personDTO.getId()).isPresent()) {
            if (post.getAuthorId() == personDTO.getId()){
                post.setMyLike(false);
                postRepository.save(post);
            }
            PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, personDTO.getId()).get();
            postLikeRepository.delete(postLike);
        }
        else throw new PostException("Not liked", HttpStatus.BAD_REQUEST);
    }

    public List<PostDTO> getAllPosts(){
        return postRepository.findAll().stream().map(postMapper::toDTO).collect(Collectors.toList());
    }

    public Page<PostDTO> getAllPosts(String searchedTitle, Integer page){
        Specification<Post> spec = Specification.where(null);
        if (searchedTitle != null){
            spec = spec.and(PostSpecification.likeSearchedTitle(searchedTitle));
        }
        Page<Post> posts = postRepository.findAll(spec,PageRequest.of(page - 1, 20));
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

