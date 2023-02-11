package com.example.demo.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_id_gen")
    @SequenceGenerator(name = "comments_id_gen", sequenceName = "comments_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "post_id")
    private Long postId;
    private LocalDateTime time;
    @Column(name = "edit_time")
    private LocalDateTime editTime;
    private Long authorId;
    private String text;
    private boolean isBlocked;
    private boolean isDelete;
    private Long likeAmount;
    private boolean myLike;
    private String imagepath;
}


