package io.tmaitz.bearcoinexserver.config;

import io.tmaitz.bearcoinexserver.handler.WsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WsHandler wsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, "/ws-chat")
                .setAllowedOrigins("*");
    }

}
