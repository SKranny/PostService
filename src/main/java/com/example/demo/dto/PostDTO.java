package com.example.demo.dto;
import lombok.Data;
import java.util.Date;

@Data
public class PostDTO {
    private long id;
    private Date time;
    private long authorId;
    private String title;
    private String postText;
    private boolean isBlocked;
}
