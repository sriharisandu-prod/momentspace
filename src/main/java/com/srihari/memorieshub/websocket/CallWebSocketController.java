package com.srihari.memorieshub.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CallWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/call")
    public void handleCall(
            CallSignal signal
    ) {

        if (signal == null) {
            return;
        }

        if (signal.getReceiverId() == null) {
            return;
        }

        messagingTemplate.convertAndSend(
                "/queue/calls/" +
                        signal.getReceiverId(),
                signal
        );
    }
}
