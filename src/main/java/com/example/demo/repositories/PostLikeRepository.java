package com.example.demo.repositories;

import com.example.demo.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository <PostLike, Long> {

    @Query(value = "SELECT pl.* FROM post_likes pl JOIN post2like p2l ON p2l.post_like_id = pl.id " +
            "JOIN post p ON p2l.post_id = p.id WHERE p.id = :postId AND p.author_id = :userId", nativeQuery = true)
    Optional<PostLike> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);


    @Query(value = "SELECT COUNT(*) FROM post2like WHERE post_id = :postId", nativeQuery = true)
    Long getLikesCount(@Param("postId") Long postId);
}
