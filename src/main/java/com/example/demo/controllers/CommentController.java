package com.example.demo.controllers;

import com.example.demo.dto.comment.CommentDTO;
import com.example.demo.dto.comment.CommentRequest;
import com.example.demo.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import security.TokenAuthentication;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name="Comment Service", description="Сервис работы с комментариями")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Редактирование комментария по ID")
    @PutMapping("/{id}/comment/{commentId}")
    @ResponseBody
    public void editComment(@PathVariable("id") Long postId, @PathVariable("commentId") Long commentId,
                            @RequestBody @Parameter(description = "Комментарий") CommentRequest commentRequest,
                            @PathVariable Long id){
        commentService.editComment(postId, commentId, commentRequest, id);
    }

    @Operation(summary = "Удаление комментария по ID")
    @DeleteMapping("/{id}/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") Long postId, @PathVariable("commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
    }

    @Operation(summary = "Получить все комментарии")
    @GetMapping("{id}/comment")
    @ResponseBody
    public List<CommentDTO> getAllComments(@PathVariable Long id){
        return commentService.getAllComments(id);
    }

    @Operation(summary = "Создать комментарий")
    @PostMapping("/{id}/comment")
    @ResponseBody
    public CommentDTO addComment( @PathVariable Long id,
                                  @RequestBody @Parameter(description = "Комментарий") CommentRequest commentRequest,
                                  TokenAuthentication authentication){
        return commentService.addComment(id, commentRequest, authentication.getTokenData().getEmail());
    }

}


