package com.srihari.memorieshub.post.service;

import com.srihari.memorieshub.post.dto.PostResponseDto;
import com.srihari.memorieshub.post.entity.Category;
import com.srihari.memorieshub.post.entity.Visibility;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostResponseDto createPost(
            String title,
            String description,
            Category category,
            String location,
            Visibility visibility,
            String currentUserEmail,
            List<MultipartFile> files
    ) throws IOException;

    PostResponseDto getPostById(Long postId);

    List<PostResponseDto> getAllPosts();

    PostResponseDto updatePost(
            Long postId,
            String title,
            String description,
            Category category,
            String location,
            Visibility visibility,
            List<MultipartFile> files,
            String currentUserEmail
    ) throws IOException;

    void deletePost(
            Long postId,
            String currentUserEmail
    ) throws IOException;

    void deletePostMedia(
            Long postId,
            Long mediaId,
            String currentUserEmail
    ) throws IOException;
}