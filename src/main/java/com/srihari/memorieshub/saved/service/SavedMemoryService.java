package com.srihari.memorieshub.saved.service;


import com.srihari.memorieshub.saved.dto.SavedMemoryResponseDto;

import java.util.List;

public interface SavedMemoryService {

    SavedMemoryResponseDto saveMemory(
            Long userId,
            Long postId
    );

    void unsaveMemory(
            Long userId,
            Long postId
    );

    boolean isSaved(
            Long userId,
            Long postId
    );

    List<Long> getSavedPostIds(
            Long userId
    );
}