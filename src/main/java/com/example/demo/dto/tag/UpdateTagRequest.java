package com.example.demo.dto.tag;

import lombok.Data;

@Data
public class UpdateTagRequest {
    private Long tagId;

    private String text;
}
