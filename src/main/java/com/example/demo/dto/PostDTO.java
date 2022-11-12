package com.example.demo.dto;
import lombok.Data;
import java.util.Date;

@Data
public class PostDTO {
    private long id;
    private Date time;
    private long author_id;
    private String title;
    private String post_text;
    private boolean is_blocked;
}
