package com.freelancer.freelancer_platform.handler;

import org.springframework.stereotype.Component;
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
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        System.out.println("New WebSocket connection: " + session.getId()); // log üçün
        if (session.isOpen()) {
            session.sendMessage(new TextMessage("Hello! WebSocket is working."));
        }        sessions.remove(session);
        broadcastOnlineUsers();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Əgər client mesaj göndərirsə, burada işləyə bilərsən
    }

    private void broadcastOnlineUsers() throws Exception {
        String message = "Online users count: " + sessions.size();
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                session.sendMessage(new TextMessage(message));

            }
        }
    }
}
