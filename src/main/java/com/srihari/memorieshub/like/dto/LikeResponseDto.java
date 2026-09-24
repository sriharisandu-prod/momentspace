package com.srihari.memorieshub.like.dto;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponseDto {

    private Long id;
    private Long userId;
    private Long postId;
    private long totalLikes;
}

