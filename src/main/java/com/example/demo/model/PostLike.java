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
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_like_id_gen")
    @SequenceGenerator(name = "post_like_id_gen", sequenceName = "post_like_id_seq", allocationSize = 1)
    @Column(name = "id")
    Long id;
    Long userId;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "post2like",
            joinColumns = { @JoinColumn(name = "post_like_id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id") }
    )
    private Set<Post> posts = new HashSet<>();

}
