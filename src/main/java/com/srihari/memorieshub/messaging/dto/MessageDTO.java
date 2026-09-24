package com.srihari.memorieshub.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long id;

    private String content;

    private String senderEmail;

    private String receiverEmail;

    private LocalDateTime sentAt;
}
