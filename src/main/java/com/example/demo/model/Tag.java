package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_gen")
    @SequenceGenerator(name = "tag_id_gen", sequenceName = "tag_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    private String tag;

    @ManyToMany(mappedBy = "tagSet")
    @JsonIgnore
    Set<Post> posts;
}

