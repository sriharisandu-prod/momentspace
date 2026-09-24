package com.srihari.memorieshub.follow.controller;


import com.srihari.memorieshub.follow.dto.FollowResponseDto;
import com.srihari.memorieshub.follow.service.FollowService;
import com.srihari.memorieshub.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<FollowResponseDto> followUser(
            @RequestParam Long followerId,
            @RequestParam Long followingId
    ) {
        return ResponseEntity.ok(
                followService.followUser(
                        followerId,
                        followingId
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<String> unfollowUser(
            @RequestParam Long followerId,
            @RequestParam Long followingId
    ) {
        followService.unfollowUser(
                followerId,
                followingId
        );

        return ResponseEntity.ok(
                "User unfollowed successfully"
        );
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<Long> getFollowersCount(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                followService.getFollowersCount(userId)
        );
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<Long> getFollowingCount(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                followService.getFollowingCount(userId)
        );
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkFollowing(
            @RequestParam Long followerId,
            @RequestParam Long followingId
    ) {
        return ResponseEntity.ok(
                followService.isFollowing(
                        followerId,
                        followingId
                )
        );
    }

    // ================================
    // NEW: FOLLOWERS LIST
    // ================================

    @GetMapping("/followers/list/{userId}")
    public ResponseEntity<List<UserResponseDto>> getFollowers(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                followService.getFollowers(userId)
        );
    }

    // ================================
    // NEW: FOLLOWING LIST
    // ================================

    @GetMapping("/following/list/{userId}")
    public ResponseEntity<List<UserResponseDto>> getFollowing(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                followService.getFollowing(userId)
        );
    }
}