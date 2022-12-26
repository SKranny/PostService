package com.example.demo;

import com.example.demo.dto.PostDTO;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class PostMapperTest {

    private PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    Post post = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("Some title")
            .postText("Some text")
            .isBlocked(false)
            .build();

    PostDTO postDTO = PostDTO.builder()
            .id(1L)
            .authorId(1L)
            .title("Some title")
            .postText("Some text")
            .isBlocked(false)
            .build();
    @Test
   public void mapPostToDtoTest() {
System.out.println(postMapper);

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



}
