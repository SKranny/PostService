package com.example.demo;

import com.example.demo.dto.TagDTO;
import com.example.demo.mappers.TagMapper;
import com.example.demo.model.Tag;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TagMapperTest {

    private TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    Tag tag = Tag.builder()
            .id(1L)
            .tag("#SomeTag")
            .build();

    TagDTO tagDTO = TagDTO.builder()
            .id(1L)
            .tag("#SomeTag")
            .build();

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
}
