package com.freelancer.freelancer_platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private boolean isRead = false;
    private LocalDateTime sendAt;
    private LocalDateTime readAt;
    @ManyToMany
    @JoinColumn(name = "sender_id", nullable = false)
    private User receiver;
    @ManyToMany
    @JoinColumn(name = "receiver_id", nullable = false)
    private User sender;
}
