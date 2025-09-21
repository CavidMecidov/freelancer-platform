package com.freelancer.freelancer_platform.controller;

import com.freelancer.freelancer_platform.entity.Message;
import com.freelancer.freelancer_platform.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/send")
    public ResponseEntity<Message> send(@RequestParam Long receiverId,
                                        @RequestParam String content) {
        Message send = messageService.sendMessage(receiverId, content);
        return ResponseEntity.ok(send);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/inbox")
    public ResponseEntity<List<Message>> getInbox() {
        List<Message> inbox = messageService.getInbox();
        return ResponseEntity.ok(inbox);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(@RequestParam Long otherUserId) {
        List<Message> conversation = messageService.getConversation(otherUserId);
        return ResponseEntity.ok(conversation);
    }

    public ResponseEntity<String> markAsRead(@RequestParam Long messageId) {
        messageService.markAsRead(messageId);
        return ResponseEntity.ok("Message marked as read");
    }

}
