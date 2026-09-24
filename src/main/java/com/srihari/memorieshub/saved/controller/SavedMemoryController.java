package com.srihari.memorieshub.saved.controller;


import com.srihari.memorieshub.saved.dto.SavedMemoryResponseDto;
import com.srihari.memorieshub.saved.service.SavedMemoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-memories")
@RequiredArgsConstructor
public class SavedMemoryController {

    private final SavedMemoryService savedMemoryService;

    /*
     * SAVE
     *
     * POST
     * /api/saved-memories?userId=1&postId=10
     */
    @PostMapping
    public ResponseEntity<SavedMemoryResponseDto> saveMemory(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {

        return ResponseEntity.ok(
                savedMemoryService.saveMemory(
                        userId,
                        postId
                )
        );
    }

    /*
     * UNSAVE
     *
     * DELETE
     * /api/saved-memories?userId=1&postId=10
     */
    @DeleteMapping
    public ResponseEntity<String> unsaveMemory(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {

        savedMemoryService.unsaveMemory(
                userId,
                postId
        );

        return ResponseEntity.ok(
                "Memory removed from saved memories"
        );
    }

    /*
     * CHECK
     *
     * GET
     * /api/saved-memories/check?userId=1&postId=10
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkSaved(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {

        return ResponseEntity.ok(
                savedMemoryService.isSaved(
                        userId,
                        postId
                )
        );
    }

    /*
     * GET ALL SAVED POST IDS
     *
     * GET
     * /api/saved-memories/user/1
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Long>> getSavedPostIds(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                savedMemoryService
                        .getSavedPostIds(userId)
        );
    }
}
