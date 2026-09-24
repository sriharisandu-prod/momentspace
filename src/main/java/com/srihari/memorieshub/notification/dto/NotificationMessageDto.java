package com.srihari.memorieshub.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationMessageDto {

    private Long id;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String message;

    private String type;

    private boolean isRead;

    private LocalDateTime createdAt;
}
