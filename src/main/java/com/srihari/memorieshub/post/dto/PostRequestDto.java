package com.srihari.memorieshub.post.dto;

import com.srihari.memorieshub.post.entity.Category;
import com.srihari.memorieshub.post.entity.Visibility;
import lombok.Data;

@Data
public class PostRequestDto {

    private String title;

    private String description;

    private Category category;

    private String location;

    private Visibility visibility;

    private Long userId;
}
