package com.example.demo.mappers;

import com.example.demo.model.Post;
import dto.postDto.PostDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDTO(Post post);
    Post toPost (PostDTO postDTO);
}
