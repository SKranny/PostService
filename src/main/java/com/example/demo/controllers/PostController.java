package com.example.demo.controllers;

import com.example.demo.services.PostService;
import dto.postDto.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name="Post Service", description="Сервис работы с постами")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Получение сообщения по ID")
    @GetMapping("/{id}")
    @ResponseBody
    public PostDTO getPostById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @Operation(summary = "Редактирование сообщения по ID")
    @PutMapping("/{id}")
    public ResponseEntity editPost(@PathVariable @RequestParam @Parameter(description = "заголовок") Long id,
                                   @RequestParam @Parameter(description = "текст поста") String title,
                                   @RequestParam @Parameter(description = "ID автора") String text){
        return postService.editPost(id, title, text);
    }

    @Operation(summary = "Удаление сообщения по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return postService.delete(id);
    }

    @Operation(summary = "Получить все сообщения")
    @GetMapping
    @ResponseBody
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @Operation(summary = "Создать сообщение")
    @PostMapping
    @ResponseBody
    public ResponseEntity createPost(@RequestParam @Parameter(description = "заголовок") String title,
                                     @RequestParam  @Parameter(description = "текст поста") String text,
                                     @RequestParam @Parameter(description = "ID автора") Long authorId){
        return postService.createPost(title, text, authorId);
    }

}
