package com.srihari.memorieshub.messaging.service;

import com.srihari.memorieshub.messaging.model.Message;
import com.srihari.memorieshub.messaging.repository.MessageRepository;
import com.srihari.memorieshub.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;

    public Message saveMessage(
            User sender,
            User receiver,
            String content
    ) {

        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Message cannot be empty");
        }

        Message message = new Message();

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content.trim());
        message.setSentAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getConversation(
            User user1,
            User user2
    ) {

        return messageRepository
                .findBySenderAndReceiverOrSenderAndReceiverOrderBySentAtAsc(
                        user1,
                        user2,
                        user2,
                        user1
                );
    }
}