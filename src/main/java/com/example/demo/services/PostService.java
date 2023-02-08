package com.example.demo.services;

import com.example.demo.dto.post.UpdatePostRequest;
import com.example.demo.dto.tag.UpdateTagRequest;
import com.example.demo.exception.PostException;
import com.example.demo.feign.PersonService;
import com.example.demo.dto.post.CreatePostRequest;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.model.Tag;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.TagRepository;
import dto.postDto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import security.dto.TokenData;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
        Post post = findPostById(req.getPostId(), tokenData.getId());

        post.setTitle(req.getTitle());
        post.setPostText(req.getPostText());
        post.setIsBlocked(req.getIsBlocked());
        post.setWithFriends(req.getWithFriends());
        updateTags(req.getUpdateReqs(), post);

        return postMapper.toDTO(postRepository.save(post));
    }

    public void delete(Long id){
        Post post = findPostById(id);

        post.setIsDelete(true);
        post.setIsBlocked(true);

        postRepository.save(post);
    }

    public void createPost(CreatePostRequest req, TokenData tokenData){
        Post post = Post.builder()
                .title(req.getTitle())
                .postText(req.getText())
                .authorId(tokenData.getId())
                .time(LocalDateTime.now())
                .tags(getOrBuildTags(req.getTags()))
                .build();
        postRepository.save(post);
    }

    public Page<PostDTO> getAllPostsByUser(String email, Pageable pageable){
        return getAllPostsByUser(personService.getPersonDTOByEmail(email).getId(), pageable);
    }

    public Page<PostDTO> getAllPostsByUser(Long id, Pageable pageable) {
        List<PostDTO> posts = postRepository.findAllByAuthorIdAndIsDeleteIsFalse(id, pageable).get()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(posts);
    }

    public Page<PostDTO> findAllPosts(Boolean withFriends, LocalDateTime toTime, LocalDateTime fromTime,
                                      Boolean isDelete, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page, offset, Sort.by("time").descending());
        List<PostDTO> posts = postRepository.findAllByFilter(withFriends, isDelete, toTime, fromTime, pageable).get()
                .map(postMapper::toDTO)
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
}

