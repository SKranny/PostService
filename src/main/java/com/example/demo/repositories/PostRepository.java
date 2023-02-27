package com.example.demo.repositories;

import com.example.demo.constants.PostType;
import com.example.demo.model.Post;
import com.example.demo.repositories.specifications.PostSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor {
    Page<Post> findAllByAuthorIdAndIsDeleteIsFalseAndPublishTimeBeforeOrderByPublishTimeDesc(Long author_id, ZonedDateTime now, Pageable pageable);
    Page<Post> findByAuthorIdInAndIsDeleteIsFalseOrderByPublishTimeDesc(Collection<Long> authorId, Pageable pageable);
    Page<Post> findAllByAuthorIdAndType(Long id, PostType postType, Pageable pageable);
    Optional<Post> findByIdAndAuthorId(Long id, Long authorId);

    default List<Post> findAllByFilter(Boolean withFriends, Boolean isDelete, ZonedDateTime now,
                                       ZonedDateTime toTime, ZonedDateTime fromTime) {
        Specification<Post> specification = Specification.where(PostSpecification.isWithFriends(withFriends))
                .and(PostSpecification.postedPost(now))
                .and(PostSpecification.isDeletePost(isDelete))
                .and(PostSpecification.toTime(Optional.ofNullable(toTime).orElse(ZonedDateTime.now())))
                .and(PostSpecification.fromTime(Optional.ofNullable(fromTime).orElse(ZonedDateTime.now().minusYears(1))));
        return this.findAll(specification);
    }

    default long countByFilter(Boolean withFriends, Boolean isDelete, ZonedDateTime now,
                               ZonedDateTime toTime, ZonedDateTime fromTime) {
        Specification<Post> specification = Specification.where(PostSpecification.isWithFriends(withFriends))
                .and(PostSpecification.postedPost(now))
                .and(PostSpecification.isDeletePost(isDelete))
                .and(PostSpecification.toTime(Optional.ofNullable(toTime).orElse(ZonedDateTime.now())))
                .and(PostSpecification.fromTime(Optional.ofNullable(fromTime).orElse(ZonedDateTime.now().minusYears(1))));
        return this.count(specification);
    }

    List<Post> findAllPostsByIsBlockedIsTrue();

    List<Post> findAllPostsByIsBlockedIsFalseAndIsDeleteIsFalse();

    List<Post> findAllPostsByTimeBetween(LocalDate date1, LocalDate date2);

    long countAllByTagsIsNotNull();

}
