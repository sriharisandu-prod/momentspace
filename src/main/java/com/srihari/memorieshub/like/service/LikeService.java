package com.srihari.memorieshub.like.service;



import com.srihari.memorieshub.like.dto.LikeRequestDto;
import com.srihari.memorieshub.like.dto.LikeResponseDto;
import com.srihari.memorieshub.notification.service.NotificationService;

public interface LikeService {


    LikeResponseDto likePost(
            LikeRequestDto requestDto
    );

    void unlikePost(
            Long userId,
            Long postId
    );

    long getLikeCount(
            Long postId
    );
    boolean isPostLikedByUser(
            Long userId,
            Long postId
    );

}