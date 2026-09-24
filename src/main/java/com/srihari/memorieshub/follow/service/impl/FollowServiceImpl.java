package com.srihari.memorieshub.follow.service.impl;


import com.srihari.memorieshub.follow.dto.FollowResponseDto;
import com.srihari.memorieshub.follow.entity.Follow;
import com.srihari.memorieshub.follow.mapper.FollowMapper;
import com.srihari.memorieshub.follow.repository.FollowRepository;
import com.srihari.memorieshub.follow.service.FollowService;
import com.srihari.memorieshub.notification.entity.NotificationType;
import com.srihari.memorieshub.notification.service.NotificationService;
import com.srihari.memorieshub.user.dto.UserResponseDto;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.srihari.memorieshub.user.mapper.UserMapper;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    // IMPORTANT:
    // This must be UserMapper, not FollowMapper
    private final UserMapper userMapper;

    private final FollowMapper followMapper;

    private final NotificationService notificationService;


    // =========================================
    // FOLLOW USER
    // =========================================

    @Override
    public FollowResponseDto followUser(
            Long followerId,
            Long followingId) {

        if (followerId.equals(followingId)) {
            throw new RuntimeException(
                    "User cannot follow themselves"
            );
        }

        if (followRepository
                .existsByFollowerIdAndFollowingId(
                        followerId,
                        followingId)) {

            throw new RuntimeException(
                    "Already following this user"
            );
        }

        User follower = userRepository
                .findById(followerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Follower not found"
                        )
                );

        User following = userRepository
                .findById(followingId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User to follow not found"
                        )
                );

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .followedAt(LocalDateTime.now())
                .build();

        Follow savedFollow =
                followRepository.save(follow);

        long followersCount =
                followRepository.countByFollowingId(
                        followingId
                );

        long followingCount =
                followRepository.countByFollowerId(
                        followerId
                );


        // =========================================
        // NOTIFICATION
        // =========================================

        notificationService.createNotification(
                follower,
                following,
                follower.getUsername()
                        + " started following you",
                NotificationType.FOLLOW
        );


        return followMapper.toDto(
                savedFollow,
                followersCount,
                followingCount
        );
    }


    // =========================================
    // UNFOLLOW USER
    // =========================================

    @Override
    public void unfollowUser(
            Long followerId,
            Long followingId) {

        Follow follow = followRepository
                .findByFollowerIdAndFollowingId(
                        followerId,
                        followingId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Follow relationship not found"
                        )
                );

        followRepository.delete(follow);
    }


    // =========================================
    // GET FOLLOWERS
    // =========================================

    @Override
    public List<UserResponseDto> getFollowers(
            Long userId) {

        List<Follow> follows =
                followRepository.findByFollowingId(
                        userId
                );

        return follows.stream()
                .map(follow ->
                        userMapper.toResponseDto(
                                follow.getFollower()
                        )
                )
                .toList();
    }


    // =========================================
    // GET FOLLOWING
    // =========================================

    @Override
    public List<UserResponseDto> getFollowing(
            Long userId) {

        List<Follow> follows =
                followRepository.findByFollowerId(
                        userId
                );

        return follows.stream()
                .map(follow ->
                        userMapper.toResponseDto(
                                follow.getFollowing()
                        )
                )
                .toList();
    }


    // =========================================
    // FOLLOWERS COUNT
    // =========================================

    @Override
    public long getFollowersCount(
            Long userId) {

        return followRepository
                .countByFollowingId(userId);
    }


    // =========================================
    // FOLLOWING COUNT
    // =========================================

    @Override
    public long getFollowingCount(
            Long userId) {

        return followRepository
                .countByFollowerId(userId);
    }


    // =========================================
    // CHECK FOLLOWING
    // =========================================

    @Override
    public boolean isFollowing(
            Long followerId,
            Long followingId) {

        return followRepository
                .existsByFollowerIdAndFollowingId(
                        followerId,
                        followingId
                );
    }
}