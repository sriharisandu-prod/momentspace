package com.srihari.memorieshub.user.mapper;


import com.srihari.memorieshub.user.dto.UserResponseDto;
import com.srihari.memorieshub.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public  UserResponseDto toResponseDto(User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());

        dto.setUsername(user.getUsername());

        dto.setEmail(user.getEmail());

        dto.setProfilePhotoUrl(user.getProfilePhotoUrl());

        dto.setProfilePhotoPublicId(user.getProfilePhotoPublicId());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;

    }

}