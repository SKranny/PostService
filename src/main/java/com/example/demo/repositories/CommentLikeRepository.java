package com.example.demo.repositories;

import com.example.demo.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository  <CommentLike, Long> {

    @Query(value = "SELECT c.id AS commentId, c.post_id AS postId, cl.user_id AS userId, cl.id AS commentLikeId " +
            "FROM comments c JOIN comment2like c2cl ON c.id = c2cl.comment_id " +
            "JOIN comment_likes cl ON c2cl.comment_like_id = cl.id", nativeQuery = true)
    Optional<CommentLike> findByCommentIdAndPostId();

    @Query(value = "SELECT cl.id, cl.user_id FROM comment2like c2l INNER JOIN comment_likes cl ON c2l.comment_like_id = cl.id " +
            "INNER JOIN comments c ON c2l.comment_id = c.id INNER JOIN post p ON c.post_id = p.id " +
            "WHERE cl.user_id = :userId AND c.id = :commentId AND p.id = :postId", nativeQuery = true)
    Optional<CommentLike> findByCommentIdPostIdUserId(@Param("commentId") Long commentId,
                                                      @Param("postId") Long postId, @Param("userId") Long userId);

}
