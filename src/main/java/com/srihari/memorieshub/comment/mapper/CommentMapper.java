package com.srihari.memorieshub.comment.mapper;

import com.srihari.memorieshub.comment.dto.CommentResponseDto;
import com.srihari.memorieshub.comment.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDto toDto(
            Comment comment
    ){

        return CommentResponseDto.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}