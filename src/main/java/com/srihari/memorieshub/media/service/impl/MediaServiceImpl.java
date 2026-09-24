package com.srihari.memorieshub.media.service.impl;

import com.cloudinary.Cloudinary;

import com.cloudinary.utils.ObjectUtils;

import com.srihari.memorieshub.media.dto.MediaResponseDto;

import com.srihari.memorieshub.media.service.MediaService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Map;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final Cloudinary cloudinary;

    @Override
    public MediaResponseDto uploadMedia(
            MultipartFile file
    ) throws IOException {

        validateFile(file);

        Map<?, ?> uploadResult =
                cloudinary
                        .uploader()
                        .upload(
                                file.getBytes(),
                                ObjectUtils.asMap(
                                        "resource_type",
                                        "auto"
                                )
                        );

        MediaResponseDto dto =
                new MediaResponseDto();

        dto.setUrl(
                uploadResult
                        .get("secure_url")
                        .toString()
        );

        dto.setPublicId(
                uploadResult
                        .get("public_id")
                        .toString()
        );

        dto.setResourceType(
                uploadResult
                        .get("resource_type")
                        .toString()
        );

        return dto;
    }

    @Override
    public List<MediaResponseDto> uploadMultipleMedia(
            List<MultipartFile> files
    ) throws IOException {

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one file is required"
            );
        }

        List<MediaResponseDto> mediaList =
                new ArrayList<>();

        for (MultipartFile file : files) {

            MediaResponseDto media =
                    uploadMedia(file);

            mediaList.add(media);
        }

        return mediaList;
    }

    private void validateFile(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "File cannot be empty"
            );
        }

        String contentType =
                file.getContentType();

        if (contentType == null) {

            throw new IllegalArgumentException(
                    "Unable to determine file type"
            );
        }

        boolean validImage =
                contentType.startsWith("image/");

        boolean validVideo =
                contentType.startsWith("video/");

        if (!validImage && !validVideo) {

            throw new IllegalArgumentException(
                    "Only image and video files are allowed"
            );
        }
    }
}
