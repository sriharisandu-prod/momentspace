package com.srihari.memorieshub.media.service;

import com.srihari.memorieshub.media.dto.MediaResponseDto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MediaService {

    MediaResponseDto uploadMedia(
            MultipartFile file
    ) throws IOException;

    List<MediaResponseDto> uploadMultipleMedia(
            List<MultipartFile> files
    ) throws IOException;
}