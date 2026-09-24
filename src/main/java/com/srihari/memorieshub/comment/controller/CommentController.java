package com.srihari.memorieshub.comment.controller;

import com.srihari.memorieshub.comment.dto.CommentRequestDto;
import com.srihari.memorieshub.comment.dto.CommentResponseDto;
import com.srihari.memorieshub.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto>
    createComment(
            @RequestBody CommentRequestDto requestDto
    ){

        return ResponseEntity.ok(
                commentService.createComment(
                        requestDto
                )
        );
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>>
    getCommentsByPostId(
            @PathVariable Long postId
    ){

        return ResponseEntity.ok(
                commentService.getCommentsByPostId(
                        postId
                )
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String>
    deleteComment(
            @PathVariable Long commentId
    ){

        commentService.deleteComment(commentId);

        return ResponseEntity.ok(
                "Comment deleted successfully"
        );
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Long>
    getCommentCount(
            @PathVariable Long postId
    ){

        return ResponseEntity.ok(
                commentService.getCommentCount(
                        postId
                )
        );
    }
}
