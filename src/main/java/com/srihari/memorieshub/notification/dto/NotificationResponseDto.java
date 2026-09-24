package com.srihari.memorieshub.notification.dto;

import com.srihari.memorieshub.notification.entity.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponseDto {

    private Long id;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String message;

    private NotificationType type;

    private boolean isRead;

    private LocalDateTime createdAt;
}

