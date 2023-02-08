package com.example.demo.dto.post;

import lombok.Data;

import java.util.Set;

@Data
public class CreatePostRequest {
    private String title;

    private String text;

    private Set<String> tags;
}
