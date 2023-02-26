package com.example.demo.repositories;

import com.example.demo.model.Post;
import com.example.demo.repositories.specifications.PostSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor {
    //Page<Post> findAllByAuthorIdAndIsDeleteIsFalseOrderByTimeDesc(Long author_id, Pageable pageable);
    Page<Post> findAllByAuthorIdAndIsDeleteIsFalseAndPublishTimeBeforeOrderByTimeDesc(Long author_id, LocalDateTime now, Pageable pageable);
    Page<Post> findByAuthorIdInAndIsDeleteIsFalseOrderByTimeDesc(Collection<Long> authorId, Pageable pageable);
    Optional<Post> findByIdAndAuthorId(Long id, Long authorId);

    default Page<Post> findAllByFilter(Boolean withFriends, Boolean isDelete, LocalDateTime now,
                                       LocalDateTime toTime, LocalDateTime fromTime, Pageable pageable) {
        Specification<Post> specification = Specification.where(PostSpecification.isWithFriends(withFriends))
                .and(PostSpecification.postedPost(now))
                .and(PostSpecification.isDeletePost(isDelete))
                .and(PostSpecification.toTime(Optional.ofNullable(toTime).orElse(LocalDateTime.now())))
                .and(PostSpecification.fromTime(Optional.ofNullable(fromTime).orElse(LocalDateTime.now().minusYears(1))));
        return this.findAll(specification, pageable);
    }
    List<Post> findAllByOrderByTimeDesc();

    @Query("SELECT p FROM Post p WHERE p.authorId IN (:author_id)")
    List<Post> findPostsByAuthorId(@Param("author_id") Long author_id, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE publish_time < NOW() ORDER BY publish_time DESC", nativeQuery = true)
    List<Post> findPublishedPosts();

    List<Post> findAllPostsByIsBlockedIsTrue();

    List<Post> findAllPostsByIsBlockedIsFalseAndIsDeleteIsFalse();
    List<Post> findAllPostsByTimeBetween(LocalDate date1, LocalDate date2);

}
