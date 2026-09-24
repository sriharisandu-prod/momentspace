package com.srihari.memorieshub.like.mapper;


import com.srihari.memorieshub.like.dto.LikeResponseDto;
import com.srihari.memorieshub.like.entity.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeResponseDto toDto(
            Like like,
            long totalLikes
    ) {

        return LikeResponseDto.builder()
                .id(like.getId())
                .userId(like.getUser().getId())
                .postId(like.getPost().getId())
                .totalLikes(totalLikes)
                .build();
    }
}
