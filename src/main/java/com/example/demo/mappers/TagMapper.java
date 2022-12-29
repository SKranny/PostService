package com.example.demo.mappers;



import com.example.demo.model.Tag;
import dto.postDto.TagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TagMapper {
    TagDTO toDTO(Tag tag);
    Tag toTag(TagDTO tagDTO);
}

