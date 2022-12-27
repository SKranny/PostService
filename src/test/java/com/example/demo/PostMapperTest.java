package com.example.demo;

import com.example.demo.dto.PostDTO;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class PostMapperTest {
    private final static String DEFAULT_TITLE = "New title";
    private final static String DEFAULT_TEXT = "New text";

    private PostMapper postMapper;
    private Post post;
    private PostDTO postDTO;

    @BeforeEach
    public void init(){
        post = Post.builder()
                .id(1L)
                .authorId(1L)
                .title(DEFAULT_TITLE)
                .postText(DEFAULT_TEXT)
                .isBlocked(false)
                .build();

        postDTO = PostDTO.builder()
                .id(1L)
                .authorId(1L)
                .title(DEFAULT_TITLE)
                .postText(DEFAULT_TEXT)
                .isBlocked(false)
                .build();
    }

    @Test
    public void mapPostToDtoTest() {
        PostDTO postDTO = postMapper.toDTO(post);

        Assertions.assertNotNull(postDTO);
        Assertions.assertEquals(post.getId(), postDTO.getId());
        Assertions.assertEquals(post.getTitle(), postDTO.getTitle());
        Assertions.assertEquals(post.getPostText(), postDTO.getPostText());
        Assertions.assertEquals(post.getIsBlocked(), postDTO.getIsBlocked());

    }


    @Test
    public void mapDtoToPostTest() {
        Post post = postMapper.toPost(postDTO);

        Assertions.assertNotNull(post);
        Assertions.assertEquals(postDTO.getId(), post.getId());
        Assertions.assertEquals(postDTO.getTitle(), post.getTitle());
        Assertions.assertEquals(postDTO.getPostText(), post.getPostText());
        Assertions.assertEquals(postDTO.getIsBlocked(), post.getIsBlocked());

    }

    @Autowired
    public void setPostMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }



}
