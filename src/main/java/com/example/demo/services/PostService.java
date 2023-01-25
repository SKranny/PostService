package com.example.demo.services;

import com.example.demo.exception.PostException;
import com.example.demo.feign.AuthService;
import com.example.demo.feign.LoginRequest;
import com.example.demo.feign.PersonService;
import com.example.demo.feign.PostRequest;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import dto.postDto.PostDTO;
import dto.userDto.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    PersonService personService;
    @Autowired
    AuthService authService;

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

    public List<PostDTO> getAllPostsByUser(String email){
        System.out.println(email);
        String string = authService.login(new LoginRequest("test","test2"));
        System.out.println(string);
        PersonDTO personDTO = personService.getPersonDTOByEmail(email);
        System.out.println(personDTO);

        return postRepository
                .findAll()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

}

