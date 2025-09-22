package com.freelancer.freelancer_platform.config;

import com.freelancer.freelancer_platform.handler.UserStatusWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final UserStatusWebSocketHandler userStatusWebSocketHandler;

    public WebSocketConfig(UserStatusWebSocketHandler userStatusWebSocketHandler) {
        this.userStatusWebSocketHandler = userStatusWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userStatusWebSocketHandler, "/ws/status")
                .setAllowedOrigins("*");
    }


}
