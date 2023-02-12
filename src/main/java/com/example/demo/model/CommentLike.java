package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post_likes")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_like_id_gen")
    @SequenceGenerator(name = "comment_like_id_gen", sequenceName = "comment_like_id_seq", allocationSize = 1)
    @Column(name = "id")
    Long id;
    Long userId;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "like2comment",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "comment_id") }
    )
    Set<Post> comments = new HashSet<>();
}
