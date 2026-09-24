package com.srihari.memorieshub.like.service.impl;


import com.srihari.memorieshub.like.dto.LikeRequestDto;
import com.srihari.memorieshub.like.dto.LikeResponseDto;
import com.srihari.memorieshub.like.entity.Like;
import com.srihari.memorieshub.like.mapper.LikeMapper;
import com.srihari.memorieshub.like.repository.LikeRepository;
import com.srihari.memorieshub.like.service.LikeService;
import com.srihari.memorieshub.notification.entity.NotificationType;
import com.srihari.memorieshub.notification.service.NotificationService;
import com.srihari.memorieshub.post.entity.Post;
import com.srihari.memorieshub.post.repository.PostRepository;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl
        implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeMapper likeMapper;
    private final NotificationService notificationService;

    @Override
    public LikeResponseDto likePost(
            LikeRequestDto requestDto
    ) {

        if (likeRepository.existsByUserIdAndPostId(
                requestDto.getUserId(),
                requestDto.getPostId()
        )) {

            throw new RuntimeException(
                    "Post already liked"
            );
        }

        User user = userRepository.findById(
                        requestDto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        Post post = postRepository.findById(
                        requestDto.getPostId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post not found"));

        Like like = Like.builder()
                .user(user)
                .post(post)
                .likedAt(LocalDateTime.now())
                .build();

        Like savedLike =
                likeRepository.save(like);

        notificationService.createNotification(user, post.getUser(), user.getUsername() + " liked your post", NotificationType.LIKE);
        long totalLikes =
                likeRepository.countByPostId(
                        post.getId());

        return likeMapper.toDto(
                savedLike,
                totalLikes
        );
    }

    @Override
    public void unlikePost(
            Long userId,
            Long postId
    ) {

        Like like =
                likeRepository
                        .findByUserIdAndPostId(
                                userId,
                                postId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Like not found"
                                ));

        likeRepository.delete(like);
    }

    @Override
    public long getLikeCount(
            Long postId
    ) {

        return likeRepository
                .countByPostId(postId);
    }
    @Override
    public boolean isPostLikedByUser(
            Long userId,
            Long postId
    ) {

        return likeRepository.existsByUserIdAndPostId(
                userId,
                postId
        );
    }


}
