package com.srihari.memorieshub.post.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.srihari.memorieshub.post.dto.PostResponseDto;
import com.srihari.memorieshub.post.entity.Category;
import com.srihari.memorieshub.post.entity.MediaType;
import com.srihari.memorieshub.post.entity.Post;
import com.srihari.memorieshub.post.entity.PostMedia;
import com.srihari.memorieshub.post.entity.Visibility;
import com.srihari.memorieshub.post.mapper.PostMapper;
import com.srihari.memorieshub.post.repository.PostMediaRepository;
import com.srihari.memorieshub.post.repository.PostRepository;
import com.srihari.memorieshub.post.service.PostService;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;
    private final PostMapper postMapper;

    @Override
    public PostResponseDto createPost(
            String title,
            String description,
            Category category,
            String location,
            Visibility visibility,
            String currentUserEmail,
            List<MultipartFile> files
    ) throws IOException {

        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one image or video is required"
            );
        }

        Post post = new Post();

        post.setTitle(title);
        post.setDescription(description);
        post.setCategory(category);
        post.setLocation(location);
        post.setVisibility(
                visibility != null
                        ? visibility
                        : Visibility.PUBLIC
        );
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        String folder =
                "memorieshub/users/user_" +
                        user.getId() +
                        "/posts/post_" +
                        savedPost.getId();

        for (MultipartFile file : files) {

            if (file == null || file.isEmpty()) {
                continue;
            }

            String contentType = file.getContentType();

            if (contentType == null) {
                throw new IllegalArgumentException(
                        "Unable to determine file type"
                );
            }

            MediaType mediaType;

            if (contentType.startsWith("video/")) {

                mediaType = MediaType.VIDEO;

            } else if (contentType.startsWith("image/")) {

                mediaType = MediaType.IMAGE;

            } else {

                throw new IllegalArgumentException(
                        "Only image and video files are allowed"
                );
            }

            Map<?, ?> upload =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder",
                                    folder,
                                    "resource_type",
                                    "auto"
                            )
                    );

            PostMedia media = new PostMedia();

            media.setMediaUrl(
                    upload.get("secure_url").toString()
            );

            media.setPublicId(
                    upload.get("public_id").toString()
            );

            media.setMediaType(mediaType);

            media.setPost(savedPost);

            postMediaRepository.save(media);

            /*
             * Keep the in-memory Post collection
             * synchronized with the newly saved media.
             */
            savedPost.getMediaList().add(media);
        }

        return postMapper.toResponseDto(savedPost);
    }

    @Override
    public PostResponseDto getPostById(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post Not Found")
                );

        return postMapper.toResponseDto(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(postMapper::toResponseDto)
                .toList();
    }

    @Override
    public PostResponseDto updatePost(
            Long postId,
            String title,
            String description,
            Category category,
            String location,
            Visibility visibility,
            List<MultipartFile> files,
            String currentUserEmail
    ) throws IOException {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post Not Found")
                );

        if (!post.getUser()
                .getEmail()
                .equals(currentUserEmail)) {

            throw new AccessDeniedException(
                    "You cannot edit another user's post"
            );
        }

        post.setTitle(title);
        post.setDescription(description);
        post.setCategory(category);
        post.setLocation(location);
        post.setVisibility(
                visibility != null
                        ? visibility
                        : Visibility.PUBLIC
        );

        postRepository.save(post);

        if (files != null && !files.isEmpty()) {

            String folder =
                    "memorieshub/users/user_" +
                            post.getUser().getId() +
                            "/posts/post_" +
                            post.getId();

            for (MultipartFile file : files) {

                if (file == null || file.isEmpty()) {
                    continue;
                }

                String contentType =
                        file.getContentType();

                if (contentType == null) {
                    throw new IllegalArgumentException(
                            "Unable to determine file type"
                    );
                }

                MediaType mediaType;

                if (contentType.startsWith("video/")) {

                    mediaType = MediaType.VIDEO;

                } else if (contentType.startsWith("image/")) {

                    mediaType = MediaType.IMAGE;

                } else {

                    throw new IllegalArgumentException(
                            "Only image and video files are allowed"
                    );
                }

                Map<?, ?> upload =
                        cloudinary.uploader().upload(
                                file.getBytes(),
                                ObjectUtils.asMap(
                                        "folder",
                                        folder,
                                        "resource_type",
                                        "auto"
                                )
                        );

                PostMedia media = new PostMedia();

                media.setMediaUrl(
                        upload.get("secure_url").toString()
                );

                media.setPublicId(
                        upload.get("public_id").toString()
                );

                media.setMediaType(mediaType);

                media.setPost(post);

                postMediaRepository.save(media);

                post.getMediaList().add(media);
            }
        }

        return postMapper.toResponseDto(post);
    }

    @Override
    public void deletePost(
            Long postId,
            String currentUserEmail
    ) throws IOException {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post Not Found")
                );

        if (!post.getUser()
                .getEmail()
                .equals(currentUserEmail)) {

            throw new AccessDeniedException(
                    "You cannot delete another user's post"
            );
        }

        List<PostMedia> mediaList =
                postMediaRepository.findByPostId(postId);

        for (PostMedia media : mediaList) {

            String resourceType =
                    media.getMediaType() == MediaType.VIDEO
                            ? "video"
                            : "image";

            cloudinary.uploader().destroy(
                    media.getPublicId(),
                    ObjectUtils.asMap(
                            "resource_type",
                            resourceType
                    )
            );
        }

        postRepository.delete(post);
    }

    @Override
    public void deletePostMedia(
            Long postId,
            Long mediaId,
            String currentUserEmail
    ) throws IOException {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post Not Found")
                );

        if (!post.getUser()
                .getEmail()
                .equals(currentUserEmail)) {

            throw new AccessDeniedException(
                    "You cannot modify another user's post"
            );
        }

        PostMedia media =
                postMediaRepository.findById(mediaId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Media Not Found"
                                )
                        );

        if (!media.getPost()
                .getId()
                .equals(postId)) {

            throw new IllegalArgumentException(
                    "Media does not belong to this post"
            );
        }

        String resourceType =
                media.getMediaType() == MediaType.VIDEO
                        ? "video"
                        : "image";

        cloudinary.uploader().destroy(
                media.getPublicId(),
                ObjectUtils.asMap(
                        "resource_type",
                        resourceType
                )
        );

        postMediaRepository.delete(media);
    }
}

