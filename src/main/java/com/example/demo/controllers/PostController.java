package com.example.demo.controllers;

import com.example.demo.repositories.PostRepository;
import com.example.demo.feign.PostRequest;
import com.example.demo.model.Post;
import com.example.demo.services.PostService;
import dto.postDto.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import security.TokenAuthentication;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "Post Service", description = "Сервис работы с постами")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @Operation(summary = "Получение сообщения по ID")
    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @Operation(summary = "Редактирование сообщения по ID")
    @PutMapping("/{id}")
    public PostDTO editPost(@RequestBody @Parameter(description = "Пост") PostRequest postRequest, @PathVariable Long id) {
        return postService.editPost(postRequest, id);
    }

    @Operation(summary = "Удаление сообщения по ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }

    @Operation(summary = "Получить все сообщения")
    @GetMapping
    public Page<Post> findAllPosts(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                   @RequestParam(name = "withFriends", required = false) Boolean withFriends,
                                   @RequestParam(name = "time", required = false) LocalDate time,
                                   @RequestParam(name = "isDelete", required = false) Boolean isDelete) {

        if (page < 1) {
            page = 1;
        }
        return postService.find(page, withFriends, time, isDelete);
    }


    @Operation(summary = "Создать сообщение")
    @PostMapping
    public PostDTO createPost(@RequestBody @Parameter(description = "Пост") PostRequest postRequest) {
        return postService.createPost(postRequest);
    }

    @Operation(summary = "Получение списка своих сообщений")
    @GetMapping("/me")
    public List<Post> getAllPostByUser(TokenAuthentication authentication,
                                       @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                       @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return postService.getAllPostsByUser(authentication.getTokenData().getEmail(), PageRequest.of(offset, limit));
    }

    @Operation(summary = "Получение списка всех сообщений юзера по id")
    @GetMapping("/user/{id}")
    public List<Post> getAllPostByUserId(@PathVariable Long id,
                                         @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                         @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return postRepository.findPostsByAuthorId(id, PageRequest.of(offset, limit));
    }

    @Operation(summary = "Получить все новости друзей")
    @GetMapping("/news/friends")
    @ResponseBody
    public List<PostDTO> getAllFriendsNews(){
        return postService.getAllFriendsNews();
    }

}
