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
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

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
                .post(postRepository.findById(postId).get())
                .time(time)
                .imagepath("")
                .build();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist"));
        postRepository.save(post);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }


    public void editComment(Long postId, Long commentId, CommentRequest commentRequest, Long id){
        LocalDateTime time = LocalDateTime.now();
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .map(c -> editComment(c, commentRequest.getText(), time))
                .orElseThrow(() -> new CommentException("Comment with the id doesn't exist", HttpStatus.BAD_REQUEST));
        commentRepository.save(comment);
    }
    private Comment editComment(Comment comment, String text, LocalDateTime time){
        comment.setText(text);
        comment.setEditTime(time);
        return comment;
    }

    public void deleteComment(Long postId, Long commentId){
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new CommentException("Comment with the id doesn't exist"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist"));
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

