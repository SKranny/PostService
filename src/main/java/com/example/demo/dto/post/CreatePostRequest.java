package com.example.demo.dto.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
public class CreatePostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotNull
    private Set<String> tags;

    private ZonedDateTime publishTime;
}
