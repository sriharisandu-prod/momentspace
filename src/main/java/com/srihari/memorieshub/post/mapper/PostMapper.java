package com.srihari.memorieshub.post.mapper;

import com.srihari.memorieshub.post.dto.PostMediaResponseDto;
import com.srihari.memorieshub.post.dto.PostResponseDto;
import com.srihari.memorieshub.post.entity.Post;
import com.srihari.memorieshub.post.entity.PostMedia;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PostMapper {

    public PostResponseDto toResponseDto(Post post) {

        PostResponseDto dto = new PostResponseDto();

        dto.setId(post.getId());

        dto.setTitle(post.getTitle());

        dto.setDescription(post.getDescription());

        dto.setCategory(post.getCategory());

        dto.setLocation(post.getLocation());

        dto.setVisibility(post.getVisibility());

        dto.setUserId(post.getUser().getId());

        dto.setUsername(post.getUser().getUsername());

        dto.setCreatedAt(post.getCreatedAt());

        dto.setUpdatedAt(post.getUpdatedAt());

        if (post.getMediaList() != null) {

            List<PostMediaResponseDto> mediaDtos =
                    post.getMediaList()
                            .stream()
                            .map(this::toMediaDto)
                            .toList();

            dto.setMediaList(mediaDtos);

        } else {

            dto.setMediaList(
                    Collections.emptyList()
            );
        }

        return dto;
    }

    private PostMediaResponseDto toMediaDto(
            PostMedia media
    ) {

        PostMediaResponseDto dto =
                new PostMediaResponseDto();

        dto.setId(media.getId());

        dto.setMediaUrl(
                media.getMediaUrl()
        );

        dto.setPublicId(
                media.getPublicId()
        );

        dto.setMediaType(
                media.getMediaType()
        );

        return dto;
    }
}