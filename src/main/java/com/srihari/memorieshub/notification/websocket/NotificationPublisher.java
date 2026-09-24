package com.srihari.memorieshub.notification.websocket;

import com.srihari.memorieshub.notification.dto.NotificationMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NotificationPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(
            Long receiverId,
            NotificationMessageDto dto) {

        messagingTemplate.convertAndSend(
                "/topic/notifications/" + receiverId,
                dto
        );
    }
}
