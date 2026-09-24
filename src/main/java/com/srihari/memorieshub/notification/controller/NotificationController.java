package com.srihari.memorieshub.notification.controller;

import com.srihari.memorieshub.notification.dto.NotificationResponseDto;
import com.srihari.memorieshub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponseDto>>
    getNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getNotifications(userId)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String>
    markAsRead(
            @PathVariable Long id) {

        notificationService.markAsRead(id);

        return ResponseEntity.ok(
                "Notification marked as read"
        );
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<Long>
    unreadCount(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadCount(userId)
        );
    }
}
