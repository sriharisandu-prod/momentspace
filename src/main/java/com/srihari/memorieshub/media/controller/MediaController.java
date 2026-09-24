package com.srihari.memorieshub.media.controller;

import com.srihari.memorieshub.media.dto.MediaResponseDto;

import com.srihari.memorieshub.media.service.MediaService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    // Upload one image/video
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public MediaResponseDto uploadMedia(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        return mediaService.uploadMedia(file);
    }

    // Upload multiple images/videos
    @PostMapping(
            value = "/upload-multiple",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<MediaResponseDto> uploadMultipleMedia(
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {

        return mediaService.uploadMultipleMedia(files);
    }
}
