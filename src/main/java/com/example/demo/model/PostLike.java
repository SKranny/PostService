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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "like2post",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id") }
    )
    Set<Post> posts = new HashSet<>();

}
