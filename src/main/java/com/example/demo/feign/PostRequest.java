package com.example.demo.feign;

import com.example.demo.model.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Set;

@Getter
public class PostRequest {
    @JsonProperty("title")
    private String title;
    @JsonProperty("postText")
    private String text;
    @JsonProperty("authorId")
    private Long authorId;

    @JsonProperty("tagSet")
    private Set<Tag> tagSet;
}
