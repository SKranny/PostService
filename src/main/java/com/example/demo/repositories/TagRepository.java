package com.example.demo.repositories;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagIgnoreCase(String text);
    List <Tag> findAllByTagIn(Collection<String> tags);
}
