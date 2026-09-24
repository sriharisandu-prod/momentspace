package com.srihari.memorieshub.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String profilePhotoUrl;
    private String profilePhotoPublicId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
