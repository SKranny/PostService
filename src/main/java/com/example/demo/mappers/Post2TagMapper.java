package com.example.demo.mappers;


import com.example.demo.dto.Post2TagDTO;
import com.example.demo.model.Post2Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Post2TagMapper {
    Post2TagMapper INSTANCE = Mappers.getMapper(Post2TagMapper.class);

    Post2TagDTO toDTO(Post2Tag post2Tag);
}
