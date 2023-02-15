package com.example.demo.repositories;

import com.example.demo.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository  <CommentLike, Long> {

    @Query(value = "SELECT c.id AS commentId, c.post_id AS postId, cl.user_id AS userId, cl.id AS commentLikeId " +
            "FROM comments c JOIN comment2like c2cl ON c.id = c2cl.comment_id " +
            "JOIN comment_likes cl ON c2cl.comment_like_id = cl.id", nativeQuery = true)
    Optional<CommentLike> findByCommentIdAndPostId();

}
