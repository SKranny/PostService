package com.example.demo.model;

import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_gen")
    @SequenceGenerator(name = "post_id_gen", sequenceName = "post_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    private Date time;
    private Long authorId;
    private String title;
    private String postText;
    private Boolean isBlocked;
}

