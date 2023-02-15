package com.example.demo.controllers;

import com.example.demo.dto.post.CreatePostRequest;
import com.example.demo.dto.post.UpdatePostRequest;
import com.example.demo.services.PostService;
import dto.postDto.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import security.TokenAuthentication;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "Post Service", description = "Сервис работы с постами")
public class PostController {

    private final PostService postService;

    @PutMapping
    @Operation(summary = "Редактирование постов по ID")
    public PostDTO editPost(@Valid @RequestBody UpdatePostRequest req, TokenAuthentication authentication) {
        return postService.editPost(req, authentication.getTokenData());
    }

    @DeleteMapping("/{id}/tags")
    public void deleteTag(@PathVariable Long id,
                          @Valid @NotNull @RequestParam List<Long> tagIds, TokenAuthentication authentication) {
        postService.deleteTags(id, tagIds, authentication.getTokenData());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление постов по ID")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }

    @Operation(summary = "Создать сообщение")
    @PostMapping
    public void createPost(@Valid @RequestBody @Parameter(description = "Пост") CreatePostRequest createPostRequest,
                           TokenAuthentication authentication) {
        postService.createPost(createPostRequest, authentication.getTokenData());
    }

    @Operation(summary = "Получить посты по фильтру")
    @GetMapping
    public Page<PostDTO> findAllPosts(
            @Valid @Min(0) @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @Valid @Min(0) @RequestParam(name = "offset", defaultValue = "20", required = false) Integer offset,
            @RequestParam(name = "withFriends", defaultValue = "false", required = false) Boolean withFriends,
            @RequestParam(name = "toTime", required = false) LocalDateTime toTime,
            @RequestParam(name = "fromTime", required = false) LocalDateTime fromTime,
            @RequestParam(name = "isDelete", defaultValue = "false", required = false) Boolean isDelete) {
        return postService.findAllPosts(withFriends, toTime, fromTime, isDelete,  page, offset);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение постов по ID")
    public PostDTO getPostById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @Operation(summary = "Получение списка своих сообщений")
    @GetMapping("/me")
    public Page<PostDTO> getAllPostByUser(
            TokenAuthentication authentication,
            @Valid @Min(0) @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "offset", defaultValue = "20", required = false) Integer limit) {
        return postService.getAllPostsByUser(authentication.getTokenData().getEmail(), PageRequest.of(page, limit));
    }

    @Operation(summary = "Получение списка всех сообщений юзера по id")
    @GetMapping("/user/{id}")
    public Page<PostDTO> getAllPostByUserId(
            @PathVariable Long id,
            @Valid @Min(0) @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "offset", defaultValue = "20", required = false) Integer limit) {
        return postService.getAllPostsByUser(id, PageRequest.of(page, limit));
    }

    @Operation(summary = "Получить все новости друзей")
    @GetMapping("/news/friends")
    @ResponseBody
    public List<PostDTO> getAllFriendsNews(
            @Valid @Min(0) @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "offset", defaultValue = "20", required = false) Integer limit
    ){
        return postService.getAllFriendsNews(PageRequest.of(page, limit));
    }

    @Operation(summary = "Поcтавить лайк на пост")
    @PostMapping("/{id}/like")
    @ResponseBody
    public void likePost(@PathVariable Long id, TokenAuthentication authentication){
        postService.likePost(id, authentication);
    }

    @Operation(summary = "Удалить лайк с поста")
    @DeleteMapping("/{id}/like")
    @ResponseBody
    public void deleteLikeFromPost(@PathVariable Long id, TokenAuthentication authentication){
        postService.deleteLikeFromPost(id, authentication);
    }



}
