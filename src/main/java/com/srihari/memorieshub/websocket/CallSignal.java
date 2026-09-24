package com.srihari.memorieshub.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallSignal {

    private CallSignalType type;

    private Long callerId;

    private Long receiverId;

    private Long callId;

    private String callType;

    private String data;
}
