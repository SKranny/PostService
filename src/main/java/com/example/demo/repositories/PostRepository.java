package com.example.demo.repositories;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
