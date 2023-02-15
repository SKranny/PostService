package com.example.demo.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id", nullable=false)
    private Post post;
    private LocalDateTime time;
    @Column(name = "edit_time")
    private LocalDateTime editTime;
    private Long authorId;
    private String text;
    private Boolean isBlocked;
    private Boolean isDelete;
    private Boolean myLike;
    private String imagepath;
    @ManyToMany(mappedBy = "comments")
    private Set<CommentLike> commentLikes = new HashSet<>();
}



