package com.srihari.memorieshub.messaging.controller;

import com.srihari.memorieshub.messaging.dto.MessageDTO;
import com.srihari.memorieshub.messaging.model.Message;
import com.srihari.memorieshub.messaging.service.ChatService;
import com.srihari.memorieshub.user.entity.User;
import com.srihari.memorieshub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @GetMapping("/conversation")
    public List<MessageDTO> getConversation(
            @RequestParam String userEmail,
            @RequestParam String otherUserEmail
    ) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        User otherUser = userRepository
                .findByEmail(otherUserEmail)
                .orElseThrow(() ->
                        new RuntimeException("Other user not found")
                );

        List<Message> messages =
                chatService.getConversation(
                        user,
                        otherUser
                );

        return messages.stream()
                .map(message ->
                        new MessageDTO(
                                message.getId(),
                                message.getContent(),
                                message.getSender().getEmail(),
                                message.getReceiver().getEmail(),
                                message.getSentAt()
                        )
                )
                .toList();
    }
}