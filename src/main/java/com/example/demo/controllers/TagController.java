package com.example.demo.controllers;

import com.example.demo.model.Tag;
import com.example.demo.services.TagService;
import dto.postDto.PostDTO;
import dto.postDto.TagDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag Service", description = "Сервис работы с тегами")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Сохранение тега")
    @PostMapping
    public TagDTO saveTag(@RequestBody Tag tag) {

        return tagService.saveTag(tag);

    }

    @Operation(summary = "Редактирование тега по ID")
    @PutMapping
    public TagDTO editTag(@RequestBody @Parameter(description = "Tag") Tag tag) {

        return tagService.editTag(tag);
    }

    @Operation(summary = "Получение тега по ID")
    @GetMapping("/{id}")
    public TagDTO getTagById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @Operation(summary = "Получить все сообщения")
    @GetMapping
    public List<TagDTO> getAllPosts() {
        return tagService.getAllTags();
    }

    @Operation(summary = "Удаление тега по ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }


}
