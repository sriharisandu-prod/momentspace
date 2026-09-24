package com.srihari.memorieshub.notification.service;

import com.srihari.memorieshub.notification.dto.NotificationMessageDto;
import com.srihari.memorieshub.notification.dto.NotificationResponseDto;
import com.srihari.memorieshub.notification.entity.Notification;
import com.srihari.memorieshub.notification.entity.NotificationType;
import com.srihari.memorieshub.notification.mapper.NotificationMapper;
import com.srihari.memorieshub.notification.repository.NotificationRepository;
import com.srihari.memorieshub.notification.websocket.NotificationPublisher;
import com.srihari.memorieshub.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final NotificationPublisher notificationPublisher;


    @Override
    public void createNotification(
            User sender,
            User receiver,
            String message,
            NotificationType type) {

        // ==========================================
        // VALIDATE MESSAGE
        // ==========================================

        if (message == null || message.trim().isEmpty()) {
            throw new RuntimeException(
                    "Notification message cannot be empty"
            );
        }


        // ==========================================
        // CURRENT TIME
        // ==========================================

        LocalDateTime now = LocalDateTime.now();


        // ==========================================
        // SAVE NOTIFICATION
        // ==========================================

        Notification notification =
                Notification.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .message(message)
                        .type(type)
                        .isRead(false)
                        .createdAt(now)
                        .build();

        Notification savedNotification =
                notificationRepository.save(notification);


        // ==========================================
        // WEBSOCKET DTO
        // ==========================================

        NotificationMessageDto dto =
                NotificationMessageDto.builder()
                        .id(savedNotification.getId())
                        .senderId(sender.getId())
                        .senderName(sender.getUsername())
                        .receiverId(receiver.getId())
                        .message(savedNotification.getMessage())
                        .type(type.name())
                        .isRead(savedNotification.isRead())
                        .createdAt(savedNotification.getCreatedAt())
                        .build();


        // ==========================================
        // SEND REAL-TIME NOTIFICATION
        // ==========================================

        notificationPublisher.sendNotification(
                receiver.getId(),
                dto
        );
    }


    @Override
    public List<NotificationResponseDto> getNotifications(
            Long userId) {

        return notificationRepository
                .findByReceiverIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toDto)
                .toList();
    }


    @Override
    public void markAsRead(Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                )
                        );

        notification.setRead(true);

        notificationRepository.save(notification);
    }


    @Override
    public long getUnreadCount(Long userId) {

        return notificationRepository
                .countByReceiverIdAndIsReadFalse(userId);
    }
}