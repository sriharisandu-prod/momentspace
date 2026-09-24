package com.srihari.memorieshub.post.dto;



import com.srihari.memorieshub.post.entity.MediaType;
import lombok.Data;

@Data
public class PostMediaResponseDto {

    private Long id;

    private String mediaUrl;

    private String publicId;

    private MediaType mediaType;
}
