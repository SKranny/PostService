package com.example.demo.repositories;

import com.example.demo.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long id);
    Optional<Comment> findByIdAndPostId(Long id, Long postId);


    @Query(value = "SELECT count(*) FROM comments", nativeQuery = true)
    Long getCommentsCount();

}

