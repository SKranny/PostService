package com.example.demo.controllers;

import com.example.demo.dto.PostDTO;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    private PostMapper postMapper;

    @GetMapping("/posts/{id}")
    public PostDTO get(@PathVariable long id) {
        Optional<Post> optionalTask = postRepository.findById(id);
        if (!optionalTask.isPresent()){
            return postMapper.toDTO(new Post());
        } return postMapper.toDTO(optionalTask.get());
    }

    @PostMapping("/posts")
    public PostDTO post(@RequestBody Post post) {
        Post newPost = postRepository.save(post);
        return postMapper.toDTO(newPost);
    }
}
