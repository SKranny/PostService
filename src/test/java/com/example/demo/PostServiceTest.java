package com.example.demo;

import com.example.demo.dto.PostDTO;
import com.example.demo.exception.PostException;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PostServiceTest {


    @Mock
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;

    Post post = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("Some title")
            .postText("Some text")
            .isBlocked(false)
            .build();


    @Test
    public void findByIdTest() {

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        Optional<Post> post = postRepository.findById(1L);
        if (!post.isPresent()) {
            throw new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST);
        }
        PostDTO postDTO = postMapper.toDTO(post.get());

        assertNotNull(postDTO);
        assertEquals(1L, postDTO.getId());
        assertEquals(1L, postDTO.getAuthorId());
        assertEquals("Some title", postDTO.getTitle());
        assertEquals("Some text", postDTO.getPostText());
        assertEquals(false, postDTO.getIsBlocked());
    }

    @Test
    public void editPost() {

        when(postRepository.findById(this.post.getId())).thenReturn(Optional.of(post));

        Optional<Post> post = postRepository.findById(1L);
        if (!post.isPresent()) {
            throw new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST);
        }

        String title = "New title";
        String text = "New text";

        post.get().setTitle(title);
        post.get().setPostText(text);
        when(postRepository.save(ArgumentMatchers.any(Post.class))).thenReturn(this.post);

        postRepository.save(post.get());
        PostDTO postDTO = postMapper.toDTO(postRepository.save(post.get()));

        assertNotNull(postDTO);
        assertEquals(1L, postDTO.getId());
        assertEquals(1L, postDTO.getAuthorId());
        assertEquals("New title", postDTO.getTitle());
        assertEquals("New text", postDTO.getPostText());
        assertEquals(false, postDTO.getIsBlocked());
    }

}


