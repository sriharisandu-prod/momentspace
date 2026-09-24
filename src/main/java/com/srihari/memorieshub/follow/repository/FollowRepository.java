package com.srihari.memorieshub.follow.repository;

import com.srihari.memorieshub.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository
        extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    long countByFollowingId(Long userId);

    long countByFollowerId(Long userId);

    List<Follow> findByFollowerId(Long userId);

    List<Follow> findByFollowingId(Long userId);

}
