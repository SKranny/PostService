package com.example.demo.services;

import com.example.demo.exception.TagException;
import com.example.demo.feign.PersonService;
import com.example.demo.mappers.TagMapper;
import com.example.demo.model.Tag;
import com.example.demo.repositories.TagRepository;
import dto.postDto.TagDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {


    private final PersonService personService;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;



    public TagDTO saveTag(Tag tag){
        tagRepository.save(tag);
        return tagMapper.toDTO(tag);

    }
    public TagDTO editTag(Tag tag){
        tagRepository.save(tag);
        return tagMapper.toDTO(tag);


    }
    public TagDTO findById(Long id) {
        return tagRepository
                .findById(id)
                .map(tagMapper::toDTO)
                .orElseThrow(() -> new TagException("Tag with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public List<TagDTO> getAllTags(){
        return tagRepository
                .findAll()
                .stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagException("Tag with the id doesn't exist", HttpStatus.BAD_REQUEST));
        tagRepository.delete(tag);
    }


}
