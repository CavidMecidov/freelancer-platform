package com.freelancer.freelancer_platform.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserStatusWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("New WebSocket connection: " + session.getId());
        sessions.add(session);

        // Yeni qoşulan clientə mesaj
        session.sendMessage(new TextMessage("Hello! WebSocket is working."));

        // Hamıya online sayı göstər
        broadcastOnlineUsers();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Closed WebSocket connection: " + session.getId());
        sessions.remove(session);
        broadcastOnlineUsers();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Message from client: " + message.getPayload());

        // Cavabı eyni client-ə göndərək (test üçün)
        session.sendMessage(new TextMessage("Server received: " + message.getPayload()));
    }

    private void broadcastOnlineUsers() throws Exception {
        String message = "Online users count: " + sessions.size();
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        }
    }

    public void sendMessageToUser(Long userId, String msg) throws Exception {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                // əgər session userId ilə əlaqəlidirsə → test üçün sadəcə hamıya göndər
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(msg));
                }
            }
        }
    }

}
