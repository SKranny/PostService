package com.example.demo.dto.tag;

import lombok.Data;

@Data
public class CreateTagRequest {
    private String text;

    private Long postId;
}
