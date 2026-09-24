package com.srihari.memorieshub.notification.repository;

import com.srihari.memorieshub.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverIdOrderByCreatedAtDesc(
            Long receiverId
    );

    long countByReceiverIdAndIsReadFalse(
            Long receiverId
    );
}

