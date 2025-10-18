package com.freelancer.freelancer_platform.config;

import com.freelancer.freelancer_platform.entity.ChatMessage;
import com.freelancer.freelancer_platform.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.awt.*;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username =(String)headerAccessor.getSessionAttributes().get("username");
        if(username!=null){
            Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
            var message = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .senderId(userId)
                    .timestamp(LocalDateTime.now())
                    .build();
            messageTemplate.convertAndSend("/topic/public",message );
        }

    }
}
