package com.freelancer.freelancer_platform.service;

import com.freelancer.freelancer_platform.entity.ChatMessage;
import com.freelancer.freelancer_platform.entity.Message;

import java.util.List;

public interface MessageService {
 Message sendMessage(Long receiverId,String content);
 List<Message>getConversation(Long otherUserId);
 List<Message>getInbox();
 void markAsRead(Long messageId);
 void saveMessage(ChatMessage chatMessage);
}
