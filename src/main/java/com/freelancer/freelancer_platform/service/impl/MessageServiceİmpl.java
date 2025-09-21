package com.freelancer.freelancer_platform.service.impl;

import com.freelancer.freelancer_platform.entity.Message;
import com.freelancer.freelancer_platform.entity.User;
import com.freelancer.freelancer_platform.repository.MessageRepository;
import com.freelancer.freelancer_platform.repository.UserRepository;
import com.freelancer.freelancer_platform.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceİmpl implements MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    private User getCurrenUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }

    @Override
    public Message sendMessage(Long receiverId, String content) {
        User sender = getCurrenUser();
        User receiver = userRepository.findById(receiverId).
                orElseThrow(() -> new RuntimeException("Receiver not found"));
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setRead(false);
        message.setSendAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getConversation(Long otherUserId) {
        User current = getCurrenUser();
        return messageRepository.findConversation(current.getId(), otherUserId);
    }

    @Override
    public List<Message> getInbox() {
        User current = getCurrenUser();
        return messageRepository.findByReceiver_Id(current.getId());

    }

    @Override
    public void markAsRead(Long messageId) {
        Message m = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        m.setRead(true);
        messageRepository.save(m);
    }
}
