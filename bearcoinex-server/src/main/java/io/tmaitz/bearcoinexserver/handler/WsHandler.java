package io.tmaitz.bearcoinexserver.handler;

import io.tmaitz.bearcoinexserver.registry.SessionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WsHandler extends TextWebSocketHandler {

    private final SessionRegistry sessionRegistry;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connected");
        sessionRegistry.register(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received message: {}", message);
        sessionRegistry.sendToAll(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Transport error", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Disconnected with status: {}", status);
        sessionRegistry.unregister(session);
    }

}
