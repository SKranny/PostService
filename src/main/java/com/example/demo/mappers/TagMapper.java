package com.example.demo.mappers;


import com.example.demo.dto.TagDTO;
import com.example.demo.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDTO toDTO(Tag tag);
}
