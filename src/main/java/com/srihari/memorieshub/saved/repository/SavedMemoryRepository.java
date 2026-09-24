package com.srihari.memorieshub.saved.repository;


import com.srihari.memorieshub.saved.entity.SavedMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedMemoryRepository
        extends JpaRepository<SavedMemory, Long> {

    boolean existsByUserIdAndPostId(
            Long userId,
            Long postId
    );

    Optional<SavedMemory> findByUserIdAndPostId(
            Long userId,
            Long postId
    );

    List<SavedMemory> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    void deleteByUserIdAndPostId(
            Long userId,
            Long postId
    );
}