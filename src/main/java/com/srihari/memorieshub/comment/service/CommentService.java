package com.srihari.memorieshub.comment.service;

import com.srihari.memorieshub.comment.dto.CommentRequestDto;
import com.srihari.memorieshub.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto requestDto);

    List<CommentResponseDto> getCommentsByPostId(Long postId);

    void deleteComment(Long commentId);

    long getCommentCount(Long postId);
}
