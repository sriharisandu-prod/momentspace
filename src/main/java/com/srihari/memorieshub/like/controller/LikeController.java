package com.srihari.memorieshub.like.controller;



import com.srihari.memorieshub.like.dto.LikeRequestDto;
import com.srihari.memorieshub.like.dto.LikeResponseDto;
import com.srihari.memorieshub.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponseDto>
    likePost(
            @RequestBody
            LikeRequestDto requestDto
    ) {

        return ResponseEntity.ok(
                likeService.likePost(
                        requestDto
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<String>
    unlikePost(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {

        likeService.unlikePost(
                userId,
                postId
        );

        return ResponseEntity.ok(
                "Post unliked successfully"
        );
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Long>
    getLikeCount(
            @PathVariable Long postId
    ) {

        return ResponseEntity.ok(
                likeService.getLikeCount(
                        postId
                )
        );
    }
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkLike(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {

        return ResponseEntity.ok(
                likeService.isPostLikedByUser(
                        userId,
                        postId
                )
        );
    }

}