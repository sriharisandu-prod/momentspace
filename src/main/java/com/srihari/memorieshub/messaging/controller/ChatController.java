package com.srihari.memorieshub.messaging.controller;

import com.srihari.memorieshub.messaging.dto.MessageDTO;
import com.srihari.memorieshub.messaging.model.Message;
import com.srihari.memorieshub.messaging.service.ChatService;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageDTO messageDTO) {

        User sender = userRepository
                .findByEmail(messageDTO.getSenderEmail())
                .orElseThrow(() ->
                        new RuntimeException("Sender not found")
                );

        User receiver = userRepository
                .findByEmail(messageDTO.getReceiverEmail())
                .orElseThrow(() ->
                        new RuntimeException("Receiver not found")
                );

        Message saved = chatService.saveMessage(
                sender,
                receiver,
                messageDTO.getContent()
        );

        return new MessageDTO(
                saved.getId(),
                saved.getContent(),
                sender.getEmail(),
                receiver.getEmail(),
                saved.getSentAt()
        );
    }
}