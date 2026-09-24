package com.srihari.memorieshub.post.controller;

import com.srihari.memorieshub.post.dto.PostResponseDto;
import com.srihari.memorieshub.post.entity.Category;
import com.srihari.memorieshub.post.entity.Visibility;
import com.srihari.memorieshub.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Category category,
            @RequestParam String location,
            @RequestParam(defaultValue = "PUBLIC") Visibility visibility,
            @RequestParam List<MultipartFile> files,
            Authentication authentication
    ) throws IOException {

        String currentUserEmail = authentication.getName();

        return postService.createPost(
                title,
                description,
                category,
                location,
                visibility,
                currentUserEmail,
                files
        );
    }

    @GetMapping("/{id}")
    public PostResponseDto getPostById(
            @PathVariable Long id
    ) {
        return postService.getPostById(id);
    }

    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public PostResponseDto updatePost(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Category category,
            @RequestParam String location,
            @RequestParam(defaultValue = "PUBLIC") Visibility visibility,
            @RequestParam(required = false) List<MultipartFile> files,
            Authentication authentication
    ) throws IOException {

        String currentUserEmail = authentication.getName();

        return postService.updatePost(
                id,
                title,
                description,
                category,
                location,
                visibility,
                files,
                currentUserEmail
        );
    }

    @DeleteMapping("/{id}")
    public String deletePost(
            @PathVariable Long id,
            Authentication authentication
    ) throws IOException {

        String currentUserEmail = authentication.getName();

        postService.deletePost(id, currentUserEmail);

        return "Post deleted successfully";
    }

    @DeleteMapping("/{postId}/media/{mediaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostMedia(
            @PathVariable Long postId,
            @PathVariable Long mediaId,
            Authentication authentication
    ) throws IOException {

        String currentUserEmail = authentication.getName();

        postService.deletePostMedia(
                postId,
                mediaId,
                currentUserEmail
        );
    }
}

