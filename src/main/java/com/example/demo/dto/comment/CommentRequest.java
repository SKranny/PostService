package com.example.demo.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
public class CommentRequest {
    @JsonProperty("text")
    private String text;
}

