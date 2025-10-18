package com.freelancer.freelancer_platform.controller;

import com.freelancer.freelancer_platform.entity.ChatMessage;
import com.freelancer.freelancer_platform.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage, Principal principal) {
        Long senderId = Long.parseLong(principal.getName());
        chatMessage.setSenderId(senderId);
        chatMessage.setTimestamp(LocalDateTime.now());

        // DB-yə yaz
        messageService.saveMessage(chatMessage);

        // Receiver və sender-ə mesaj göndər
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getReceiverId()), "/queue/messages", chatMessage);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(senderId), "/queue/messages", chatMessage);
    }
}
