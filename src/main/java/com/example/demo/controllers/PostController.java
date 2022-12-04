package com.example.demo.controllers;

import com.example.demo.dto.PostDTO;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } return new ResponseEntity(postMapper.toDTO(optionalPost.get()), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } return new ResponseEntity(postMapper.toDTO(optionalPost.get()), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping
    public ResponseEntity get(){
        Iterable<Post> postIterable = postRepository.findAll();
        ArrayList<Post> posts = new ArrayList<>();
        for (Post post : postIterable){
            posts.add(post);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @PostMapping
    public ResponseEntity post(){
        Iterable<Post> postIterable = postRepository.findAll();
        ArrayList<Post> posts = new ArrayList<>();
        for (Post post : postIterable){
            posts.add(post);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
