package com.srihari.memorieshub.follow.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowResponseDto {

    private Long followerId;

    private Long followingId;

    private long followersCount;

    private long followingCount;
}