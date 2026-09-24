package com.srihari.memorieshub.user.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.srihari.memorieshub.exception.UserIdNotFoundException;
import com.srihari.memorieshub.media.dto.MediaResponseDto;
import com.srihari.memorieshub.media.service.MediaService;
import com.srihari.memorieshub.user.dto.UserRequestDto;
import com.srihari.memorieshub.user.dto.UserResponseDto;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.mapper.UserMapper;
import com.srihari.memorieshub.user.repository.UserRepository;
import com.srihari.memorieshub.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MediaService mediaService;
    private final UserMapper userMapper;
    private Cloudinary cloudinary;

    @Override
    public UserResponseDto createUser(String username, String email, MultipartFile profileImage) throws IOException {

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);

        User savedUser = userRepository.save(user);

        String folder = "memorieshub/users/user_" + savedUser.getId() + "/profile";

        Map uploadResult = cloudinary.uploader().upload(profileImage.getBytes(), ObjectUtils.asMap("folder", folder));

        String imageUrl = uploadResult.get("secure_url").toString();

        String publicId = uploadResult.get("public_id").toString();

        savedUser.setProfilePhotoUrl(imageUrl);

        savedUser.setProfilePhotoPublicId(publicId);

        savedUser = userRepository.save(savedUser);

        return userMapper.toResponseDto(savedUser);
    }


@Override
public UserResponseDto getUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException("User not found with id: " + id));
    return userMapper.toResponseDto(user);
}

    @Override
    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toResponseDto)
                .toList();

    }

    @Override
    public UserResponseDto updateUser(Long id, String username, String email, MultipartFile profileImage) throws IOException {

        User user = userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException("User not found with id : " + id));

        user.setUsername(username);
        user.setEmail(email);

        if (profileImage != null && !profileImage.isEmpty()) {

            if (user.getProfilePhotoPublicId() != null) {

                cloudinary.uploader().destroy(
                        user.getProfilePhotoPublicId(),
                        ObjectUtils.emptyMap()
                );
            }

            String folder = "memorieshub/users/user_" + user.getId() + "/profile";

            Map uploadResult = cloudinary.uploader().upload(profileImage.getBytes(), ObjectUtils.asMap("folder", folder));

            String imageUrl = uploadResult.get("secure_url").toString();

            String publicId = uploadResult.get("public_id").toString();

            user.setProfilePhotoUrl(imageUrl);

            user.setProfilePhotoPublicId(publicId);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserIdNotFoundException(
                                "User not found with id : " + id
                        ));

        if (user.getProfilePhotoPublicId() != null) {

            cloudinary.uploader().destroy(
                    user.getProfilePhotoPublicId(),
                    ObjectUtils.emptyMap()
            );
        }

        userRepository.delete(user);
    }

    @Override
    public List<UserResponseDto> searchUsers(String query) {

        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        String searchText = query.trim();

        List<User> users =
                userRepository
                        .findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                                searchText,
                                searchText
                        );

        return users.stream()
                .map(userMapper::toResponseDto)
                .toList();
    }
}
