package com.example.demo.feign;

import com.example.demo.constants.PostType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
public class PostRequest {
    @JsonProperty("title")
    private String title;
    @JsonProperty("postText")
    private String text;
    @JsonProperty("authorId")
    private Long authorId;
    @JsonProperty("type")
    private PostType type;
    @JsonProperty("publishTime")
    private ZonedDateTime publishTime;


}

