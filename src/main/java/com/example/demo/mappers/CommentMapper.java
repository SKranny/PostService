package com.example.demo.mappers;

import com.example.demo.dto.comment.CommentDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toDTO(Comment comment);
    Comment toComment (CommentDTO commentDTO);

}

