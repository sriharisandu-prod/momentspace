package com.srihari.memorieshub.saved.service;


import com.srihari.memorieshub.post.entity.Post;
import com.srihari.memorieshub.post.repository.PostRepository;
import com.srihari.memorieshub.saved.dto.SavedMemoryResponseDto;
import com.srihari.memorieshub.saved.entity.SavedMemory;
import com.srihari.memorieshub.saved.repository.SavedMemoryRepository;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedMemoryServiceImpl
        implements SavedMemoryService {

    private final SavedMemoryRepository savedMemoryRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Override
    @Transactional
    public SavedMemoryResponseDto saveMemory(
            Long userId,
            Long postId
    ) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + userId
                        )
                );

        Post post = postRepository
                .findById(postId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post not found: " + postId
                        )
                );

        SavedMemory existing =
                savedMemoryRepository
                        .findByUserIdAndPostId(
                                userId,
                                postId
                        )
                        .orElse(null);

        if (existing != null) {
            return SavedMemoryResponseDto.builder()
                    .id(existing.getId())
                    .userId(userId)
                    .postId(postId)
                    .saved(true)
                    .build();
        }

        SavedMemory savedMemory =
                SavedMemory.builder()
                        .user(user)
                        .post(post)
                        .build();

        SavedMemory saved =
                savedMemoryRepository.save(
                        savedMemory
                );

        return SavedMemoryResponseDto.builder()
                .id(saved.getId())
                .userId(userId)
                .postId(postId)
                .saved(true)
                .build();
    }

    @Override
    @Transactional
    public void unsaveMemory(
            Long userId,
            Long postId
    ) {

        savedMemoryRepository
                .deleteByUserIdAndPostId(
                        userId,
                        postId
                );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSaved(
            Long userId,
            Long postId
    ) {

        return savedMemoryRepository
                .existsByUserIdAndPostId(
                        userId,
                        postId
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getSavedPostIds(
            Long userId
    ) {

        return savedMemoryRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(savedMemory ->
                        savedMemory
                                .getPost()
                                .getId()
                )
                .toList();
    }
}