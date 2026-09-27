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
    public void handleCall(CallSignal signal) {

        if (signal == null) {
            System.out.println(
                    "Received null call signal"
            );
            return;
        }

        if (signal.getType() == null) {
            System.out.println(
                    "Call signal type is null"
            );
            return;
        }

        if (signal.getReceiverId() == null) {
            System.out.println(
                    "Call receiver ID is null"
            );
            return;
        }

        System.out.println(
                "CALL SIGNAL -> " +
                        "type=" + signal.getType() +
                        ", caller=" + signal.getCallerId() +
                        ", receiver=" + signal.getReceiverId() +
                        ", callId=" + signal.getCallId()
        );

        messagingTemplate.convertAndSend(
                "/queue/calls/" +
                        signal.getReceiverId(),
                signal
        );
    }
}