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
@Table(name = "comment_likes")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_like_id_gen")
    @SequenceGenerator(name = "comment_like_id_gen", sequenceName = "comment_like_id_seq", allocationSize = 1)
    @Column(name = "id")
    Long id;
    Long userId;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "comment2like",
            joinColumns = { @JoinColumn(name = "comment_like_id") },
            inverseJoinColumns = { @JoinColumn(name = "comment_id") }
    )
    private Set<Comment> comments = new HashSet<>();
}
