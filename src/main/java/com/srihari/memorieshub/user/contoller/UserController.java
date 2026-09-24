

package com.srihari.memorieshub.user.contoller;

import com.srihari.memorieshub.user.dto.UserRequestDto;

import com.srihari.memorieshub.user.dto.UserResponseDto;

import com.srihari.memorieshub.user.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam MultipartFile profileImage
    ) throws IOException {

        return userService.createUser(
                username,
                email,
                profileImage
        );
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(
            @PathVariable Long id
    ) {

        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false)
            MultipartFile profileImage
    ) throws IOException {

        return userService.updateUser(
                id,
                username,
                email,
                profileImage
        );
    }

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id
    ) throws IOException {

        userService.deleteUser(id);

        return "User deleted successfully";
    }
    @GetMapping("/search")
    public List<UserResponseDto> searchUsers(
            @RequestParam String query
    ) {

        return userService.searchUsers(query);
    }

}