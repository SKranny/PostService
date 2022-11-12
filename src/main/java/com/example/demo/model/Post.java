package com.example.demo.model;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Post {
    @Id
    private long id;
    private Date time;
    private long authorId;
    private String title;
    private String postText;
    private boolean isBlocked;
}
