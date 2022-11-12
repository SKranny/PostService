package com.example.demo.model;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Data
@Entity
public class Post2Tag {
    @Id
    private long id;
    @ManyToMany
    private long postId;
    @ManyToMany
    private long tagId;
}
