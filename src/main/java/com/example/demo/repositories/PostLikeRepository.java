package com.example.demo.repositories;

import com.example.demo.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository <PostLike, Long> {

    @Query(value = "SELECT pl.id AS postLikeId, pl.user_id AS userId, p.id AS postId " +
            "FROM post_likes pl " +
            "JOIN post2like p2pl ON pl.id = p2pl.post_like_id " +
            "JOIN post p ON p2pl.post_id = p.id " +
            "WHERE p.id = :postId AND pl.user_id = :userId", nativeQuery = true)
    Optional<PostLike> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);
}
