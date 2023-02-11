package com.example.demo.services;

import com.example.demo.dto.comment.CommentDTO;
import com.example.demo.dto.comment.CommentRequest;
import com.example.demo.exception.CommentException;
import com.example.demo.exception.PostException;
import com.example.demo.feign.PersonService;
import com.example.demo.mappers.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import dto.userDto.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PersonService personService;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public CommentDTO addComment(Long postId, CommentRequest commentRequest, String userEmail){
        PersonDTO user = personService.getPersonDTOByEmail(userEmail);
        LocalDateTime time = LocalDateTime.now();
        Comment comment = Comment.builder()
                .authorId(user.getId())
                .isBlocked(false)
                .isDelete(false)
                .likeAmount(0L)
                .myLike(false)
                .text(commentRequest.getText())
                .postId(postId)
                .time(time)
                .imagepath("")
                .build();
        Post post = postRepository.findById(postId)
                .map(p -> {
                    p.setCommentId(comment.getId());
                    p.setCommentsCount((commentRepository.getCommentsCount() + 1));
                    return p;
                })
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
        postRepository.save(post);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    public void editComment(Long postId, Long commentId, CommentRequest commentRequest, Long id){
        LocalDateTime time = LocalDateTime.now();
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .map(c -> {
                    c.setText(commentRequest.getText());
                    c.setEditTime(time);
                    return c;
                })
                .orElseThrow(() -> new CommentException("Comment with the id doesn't exist", HttpStatus.BAD_REQUEST));
        commentRepository.save(comment);
    }

    public void deleteComment(Long postId, Long commentId){
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new CommentException("Comment with the id doesn't exist", HttpStatus.BAD_REQUEST));
        Post post = postRepository.findById(postId)
                .map(p -> {
                    p.setCommentsCount((commentRepository.getCommentsCount() - 1));
                    return p;
                })
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
        commentRepository.delete(comment);
    }

    public List<CommentDTO> getAllComments(Long postId){
        return commentRepository
                .findAllByPostId(postId)
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

}
