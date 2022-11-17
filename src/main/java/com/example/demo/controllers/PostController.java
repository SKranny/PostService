package com.example.demo.controllers;

import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts/")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{id}")
    public Post get(@PathVariable int id) {
        Optional<Post> optionalTask = postRepository.findById(id);
        if (!optionalTask.isPresent()){
            return new Post();
        } return optionalTask.get();
    }

    @PostMapping("/posts/")
    public Post post(@RequestBody Post post) {
        Post newPost = postRepository.save(post);
        return newPost;
    }


}
