package com.srihari.memorieshub.comment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {

    private Long id;

    private Long userId;

    private String username;

    private Long postId;

    private String content;

    private LocalDateTime createdAt;
}