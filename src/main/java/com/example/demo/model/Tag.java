package com.example.demo.model;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Tag {
    @Id
    private long id;
    private String tag;
}
