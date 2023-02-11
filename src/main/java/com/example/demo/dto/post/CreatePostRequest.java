package com.example.demo.dto.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CreatePostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String text;

    private Set<String> tags;

    private LocalDateTime publishTime;
}
