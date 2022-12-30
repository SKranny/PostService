package com.example.demo;

import com.example.demo.mappers.TagMapper;
import com.example.demo.model.Tag;
import dto.postDto.TagDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TagMapperTest {
    private final static String DEFAULT_TAG = "#SomeTag";

    private TagMapper tagMapper;
    private Tag tag;
    private TagDTO tagDTO;

    @BeforeEach
    public void init(){
        tag = Tag.builder()
                .id(1L)
                .tag(DEFAULT_TAG)
                .build();

        tagDTO = TagDTO.builder()
                .id(1L)
                .tag(DEFAULT_TAG)
                .build();
    }

    @Test
    public void mapTagToDtoTest() {

        TagDTO tagDTO = tagMapper.toDTO(tag);

        Assertions.assertNotNull(tagDTO);
        Assertions.assertEquals(tag.getId(), tagDTO.getId());
        Assertions.assertEquals(tag.getTag(), tagDTO.getTag());

    }

    @Test
    public void mapDtoToTagTest() {

        Tag tag = tagMapper.toTag(tagDTO);

        Assertions.assertNotNull(tag);
        Assertions.assertEquals(tagDTO.getId(), tag.getId());
        Assertions.assertEquals(tagDTO.getTag(), tag.getTag());

    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }
}
