package com.example.demo.controllers;

import com.example.demo.dto.PostDTO;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import com.example.demo.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    @ResponseBody
    public PostDTO getPostById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity editPost(@PathVariable Long id, @RequestParam String title, @RequestParam String text){
        return postService.editPost(id, title, text);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return postService.delete(id);
    }

    @GetMapping
    @ResponseBody
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity createPost(@RequestParam String title, @RequestParam String text, @RequestParam Long authorId){
        return postService.createPost(title, text, authorId);
    }

}
