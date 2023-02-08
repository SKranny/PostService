package com.example.demo.repositories;

import com.example.demo.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor {

    List<Post> findAllByOrderByTimeDesc();

    List<Post> findAllPostByAuthorId(Long id);

    @Query("SELECT p FROM Post p WHERE p.authorId IN (:author_id)")
    List<Post> findPostsByAuthorId(@Param("author_id") Long author_id, Pageable pageable);


}
