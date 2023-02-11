package com.example.demo.model;

import com.example.demo.constants.PostType;
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
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_gen")
    @SequenceGenerator(name = "post_id_gen", sequenceName = "post_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    private LocalDateTime time;

    private LocalDateTime publishTime;

    private Long authorId;

    private String title;

    private String postText;

    @Enumerated(EnumType.STRING)
    private PostType type;

    private Long commentId;

    private Long commentsCount;

    @Builder.Default
    private Boolean isBlocked = false;

    @Builder.Default
    private Boolean withFriends = false;

    @Builder.Default
    private Boolean isDelete = false;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "jt_post_tag",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

}

