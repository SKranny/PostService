package com.example.demo.services;

import com.example.demo.exception.PostException;
import com.example.demo.feign.PostRequest;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import dto.postDto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostDTO findById(Long id) {
        return postRepository
                .findById(id)
                .map(postMapper::toDTO)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public PostDTO editPost(PostRequest postRequest, Long id){
        return postRepository
                .findById(id)
                .map(p -> {
                    p.setTitle(postRequest.getTitle());
                    p.setPostText(postRequest.getText());
                    postRepository.save(p);
                    return postMapper.toDTO(p);
                })
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public void delete(Long id){
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
        postRepository.delete(post);
    }

    public List<PostDTO> getAllPosts(){
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PostDTO createPost(PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setPostText(postRequest.getText());
        post.setAuthorId(postRequest.getAuthorId());
        post.setTime(new Date());
        post.setIsBlocked(false);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    public List<PostDTO> getAllPostsByMe(){
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

}

