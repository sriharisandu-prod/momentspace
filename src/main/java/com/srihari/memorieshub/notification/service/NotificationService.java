package com.srihari.memorieshub.notification.service;

import com.srihari.memorieshub.notification.dto.NotificationResponseDto;
import com.srihari.memorieshub.notification.entity.NotificationType;
import com.srihari.memorieshub.user.entity.User;

import java.util.List;



public interface NotificationService {

    void createNotification(
            User sender,
            User receiver,
            String message,
            NotificationType type
    );

    List<NotificationResponseDto> getNotifications(
            Long userId
    );

    void markAsRead(
            Long id
    );

    long getUnreadCount(
            Long userId
    );
}
