package com.srihari.memorieshub.saved.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedMemoryResponseDto {

    private Long id;

    private Long userId;

    private Long postId;

    private boolean saved;
}