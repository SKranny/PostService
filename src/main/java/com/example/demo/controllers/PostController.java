package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts/")
public class PostController {

    @GetMapping("/posts/{id}")
    public String get() {
        return "get a post";
    }
    @PostMapping("/posts/")
    public String post() {
        return "post a post";
    }

    @PutMapping("/posts/{id}")
    public String edit(){
        return "edit a post";
    }

    @DeleteMapping("/posts/{id}")
    public String delete(){
        return "delete a post";
    }

}
