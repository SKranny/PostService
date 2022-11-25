package com.example.demo.dto;
import lombok.Data;
import java.util.Date;

@Data
public class PostDTO {
    private Long id;
    private Date time;
    private Long authorId;
    private String title;
    private String postText;
    private Boolean isBlocked;
}
