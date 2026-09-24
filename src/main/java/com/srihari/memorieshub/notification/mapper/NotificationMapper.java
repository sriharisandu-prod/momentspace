package com.srihari.memorieshub.notification.mapper;

import com.srihari.memorieshub.notification.dto.NotificationResponseDto;
import com.srihari.memorieshub.notification.entity.Notification;
import org.springframework.stereotype.Component;


@Component
public class NotificationMapper {

    public NotificationResponseDto toDto(
            Notification notification) {

        return NotificationResponseDto.builder()
                .id(notification.getId())
                .senderId(
                        notification.getSender().getId()
                )
                .senderName(
                        notification.getSender().getUsername()
                )
                .receiverId(
                        notification.getReceiver().getId()
                )
                .message(
                        notification.getMessage()
                )
                .type(
                        notification.getType()
                )
                .isRead(
                        notification.isRead()
                )
                .createdAt(
                        notification.getCreatedAt()
                )
                .build();
    }
}