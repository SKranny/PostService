package com.example.demo.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Post2Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long postId;

    private long tagId;
}
