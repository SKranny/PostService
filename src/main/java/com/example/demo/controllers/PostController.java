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
    public String get(@PathVariable int id) {
        Optional<Post> optionalTask = postRepository.findById(id);
        if (!optionalTask.isPresent()){
            return "no post found";
        } return optionalTask.get().getPostText();
    }

    @PostMapping("/posts/")
    public String post(@RequestBody Post post) {
        Post newPost = postRepository.save(post);
        return post.getId() + "saved";
    }


}
