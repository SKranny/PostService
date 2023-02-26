package com.example.demo.repositories.specifications;

import com.example.demo.model.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class PostSpecification {
    public static Specification<Post> isWithFriends(Boolean withFriends) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("withFriends"), withFriends);
    }

    public static Specification<Post> postedPost(LocalDateTime now) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("publishTime"), now);
    }


    public static Specification<Post> isDeletePost(Boolean isDelete) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDelete"), isDelete);

    }

    public static Specification<Post> toTime(LocalDateTime toTime) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("publishTime"), toTime);
    }


    public static Specification<Post> fromTime(LocalDateTime fromTime) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("publishTime"), fromTime));
    }

    public static Specification<Post> likeSearchedTitle(String searchedTitle) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%",searchedTitle)));
    }

    public static Specification<Post> isBlockedPost(Boolean isBlocked) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isBlocked"), isBlocked);
    }

    public static Specification<Post> likeSearchedTitle(String searchedTitle) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%",searchedTitle)));
    }

    public static Specification<Post> isBlockedPost(Boolean isBlocked) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isBlocked"), isBlocked);
    }
}
