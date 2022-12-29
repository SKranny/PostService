package com.example.demo;

import com.example.demo.exception.PostException;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import dto.postDto.PostDTO;
import org.junit.jupiter.api.BeforeEach;
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

    private final static String DEFAULT_TITLE = "New title";
    private final static String DEFAULT_TEXT = "New text";

    @Mock
    private PostRepository postRepository;
    private  PostMapper postMapper;
    private Post post;

    @BeforeEach
    public void init(){
        post = Post.builder()
                .id(1L)
                .authorId(1L)
                .title("Some title")
                .postText("Some text")
                .isBlocked(false)
                .build();
    }

    @Test
    public void findByIdTest() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Post post = postRepository.findById(1L).orElseThrow(
                () -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
        PostDTO postDTO = postMapper.toDTO(post);

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

        Post post = postRepository.findById(1L).orElseThrow(
                () -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));

        post.setTitle(DEFAULT_TITLE);
        post.setPostText(DEFAULT_TEXT);
        when(postRepository.save(ArgumentMatchers.any(Post.class))).thenReturn(this.post);

        postRepository.save(post);
        PostDTO postDTO = postMapper.toDTO(postRepository.save(post));

        assertNotNull(postDTO);
        assertEquals(1L, postDTO.getId());
        assertEquals(1L, postDTO.getAuthorId());
        assertEquals(DEFAULT_TITLE, postDTO.getTitle());
        assertEquals(DEFAULT_TEXT, postDTO.getPostText());
        assertEquals(false, postDTO.getIsBlocked());
    }

    @Autowired
    public void setPostMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

}


