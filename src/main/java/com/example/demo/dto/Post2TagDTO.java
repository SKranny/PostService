package com.example.demo.dto;
import lombok.Data;

import javax.persistence.ManyToMany;

@Data
public class Post2TagDTO {
    private long id;
    @ManyToMany
    private long postId;
    @ManyToMany
    private long tagId;
}
