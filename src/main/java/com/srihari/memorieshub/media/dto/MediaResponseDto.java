package com.srihari.memorieshub.media.dto;

import lombok.Getter;

import lombok.Setter;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponseDto {

    private String url;

    private String publicId;

    private String resourceType;
}