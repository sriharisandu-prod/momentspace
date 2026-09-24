package com.srihari.memorieshub.post.dto;

import com.srihari.memorieshub.post.entity.Category;
import com.srihari.memorieshub.post.entity.Post;
import com.srihari.memorieshub.post.entity.Visibility;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {

    private Long id;

    private String title;

    private String description;

    private Category category;

    private String location;

    private Visibility visibility;

    private Long userId;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<PostMediaResponseDto> mediaList;
}
