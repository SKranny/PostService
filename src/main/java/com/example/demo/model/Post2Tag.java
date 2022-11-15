package com.example.demo.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Post2Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany
    private long postId;
    @ManyToMany
    private long tagId;
}
