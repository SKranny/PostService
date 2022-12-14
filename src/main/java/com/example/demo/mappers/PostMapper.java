package com.example.demo.mappers;
import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDTO toDTO(Post post);
    Post toPost (PostDTO postDTO);
}
