package com.example.demo.dto.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CreatePostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String text;

    private Set<String> tags;
}
