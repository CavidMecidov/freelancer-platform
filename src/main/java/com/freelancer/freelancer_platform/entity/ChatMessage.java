package com.freelancer.freelancer_platform.entity;

import com.freelancer.freelancer_platform.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
public class ChatMessage {
    private Long senderId;
    private Long receiverId;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;
}