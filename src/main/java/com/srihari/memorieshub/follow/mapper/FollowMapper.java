package com.srihari.memorieshub.follow.mapper;

import com.srihari.memorieshub.follow.dto.FollowResponseDto;
import com.srihari.memorieshub.follow.entity.Follow;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {

    public FollowResponseDto toDto(
            Follow follow,
            long followersCount,
            long followingCount
    ) {

        return FollowResponseDto.builder()
                .followerId(
                        follow.getFollower().getId())
                .followingId(
                        follow.getFollowing().getId())
                .followersCount(followersCount)
                .followingCount(followingCount)
                .build();
    }
}
