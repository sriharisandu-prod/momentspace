package com.srihari.memorieshub.follow.service;

import com.srihari.memorieshub.follow.dto.FollowResponseDto;
import com.srihari.memorieshub.user.dto.UserResponseDto;

import java.util.List;

public interface FollowService {

    FollowResponseDto followUser(
            Long followerId,
            Long followingId);

    void unfollowUser(
            Long followerId,
            Long followingId);

    long getFollowersCount(
            Long userId);

    long getFollowingCount(
            Long userId);
    boolean isFollowing(
            Long followerId,
            Long followingId
    );
    List<UserResponseDto> getFollowers(Long userId);

    List<UserResponseDto> getFollowing(Long userId);

}