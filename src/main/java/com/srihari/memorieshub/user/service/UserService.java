package com.srihari.memorieshub.user.service;

import com.srihari.memorieshub.user.dto.UserRequestDto;
import com.srihari.memorieshub.user.dto.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserResponseDto createUser(String username, String email, MultipartFile profileImage) throws IOException;



    UserResponseDto getUserById(Long id);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, String username, String email, MultipartFile profileImage) throws IOException;



    void deleteUser(Long id) throws IOException;
    List<UserResponseDto> searchUsers(String query);
}
