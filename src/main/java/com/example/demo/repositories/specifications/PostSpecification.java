package com.example.demo.repositories.specifications;

import com.example.demo.model.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class PostSpecification {
    public static Specification<Post> isWithFriends(Boolean withFriends) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("withFriends"));

    }


    public static Specification<Post> isDeletePost(Boolean isDelete) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDelete"));

    }

    public static Specification<Post> time(LocalDate time) {
        return (root, criteriaQuery, criteriaBuilder) -> (Predicate) criteriaBuilder.desc(root.get("time"));

    }

}
