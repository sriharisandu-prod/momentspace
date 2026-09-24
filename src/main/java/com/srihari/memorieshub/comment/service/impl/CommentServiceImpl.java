package com.srihari.memorieshub.comment.service.impl;


import com.srihari.memorieshub.comment.dto.CommentRequestDto;
import com.srihari.memorieshub.comment.dto.CommentResponseDto;
import com.srihari.memorieshub.comment.entity.Comment;
import com.srihari.memorieshub.comment.mapper.CommentMapper;
import com.srihari.memorieshub.comment.repository.CommentRepository;
import com.srihari.memorieshub.comment.service.CommentService;
import com.srihari.memorieshub.notification.entity.NotificationType;
import com.srihari.memorieshub.notification.service.NotificationService;
import com.srihari.memorieshub.post.entity.Post;
import com.srihari.memorieshub.post.repository.PostRepository;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    private final NotificationService notificationService;


    @Override
    public CommentResponseDto createComment(
            CommentRequestDto requestDto) {

        // ==========================================
        // VALIDATE COMMENT
        // ==========================================

        if (requestDto.getContent() == null ||
                requestDto.getContent().trim().isEmpty()) {

            throw new RuntimeException(
                    "Comment content cannot be empty"
            );
        }


        // ==========================================
        // FIND USER
        // ==========================================

        User user = userRepository.findById(
                        requestDto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));


        // ==========================================
        // FIND POST
        // ==========================================

        Post post = postRepository.findById(
                        requestDto.getPostId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post not found"
                        ));


        // ==========================================
        // GET ACTUAL COMMENT TEXT
        // ==========================================

        String commentText =
                requestDto.getContent().trim();


        // ==========================================
        // CREATE COMMENT
        // ==========================================

        Comment comment = Comment.builder()
                .content(commentText)
                .user(user)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();


        Comment savedComment =
                commentRepository.save(comment);


        // ==========================================
        // CREATE NOTIFICATION
        // ==========================================

        User postOwner = post.getUser();


        // Don't notify yourself
        if (!postOwner.getId().equals(user.getId())) {

            String notificationMessage =
                    user.getUsername()
                            + " commented: \""
                            + commentText
                            + "\"";


            System.out.println(
                    "COMMENT NOTIFICATION = "
                            + notificationMessage
            );


            notificationService.createNotification(
                    user,
                    postOwner,
                    notificationMessage,
                    NotificationType.COMMENT
            );
        }


        // ==========================================
        // RETURN COMMENT
        // ==========================================

        return commentMapper.toDto(savedComment);
    }


    @Override
    public List<CommentResponseDto> getCommentsByPostId(
            Long postId) {

        return commentRepository
                .findByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }


    @Override
    public void deleteComment(
            Long commentId) {

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Comment not found"
                        ));

        commentRepository.delete(comment);
    }


    @Override
    public long getCommentCount(
            Long postId) {

        return commentRepository
                .countByPostId(postId);
    }
}