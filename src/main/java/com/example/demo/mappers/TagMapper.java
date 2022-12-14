package com.example.demo.mappers;


import com.example.demo.dto.TagDTO;
import com.example.demo.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface TagMapper {
    TagDTO toDTO(Tag tag);
    Tag toTag(TagDTO tagDTO);
}

