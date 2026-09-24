package com.srihari.memorieshub.comment.dto;

import lombok.Data;

@Data
public class CommentRequestDto {

    private Long userId;

    private Long postId;

    private String content;
}
