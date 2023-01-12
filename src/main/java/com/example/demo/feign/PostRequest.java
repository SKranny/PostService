package com.example.demo.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PostRequest {
    @JsonProperty("title")
    private String title;
    @JsonProperty("postText")
    private String text;
    @JsonProperty("authorId")
    private Long authorId;
}
