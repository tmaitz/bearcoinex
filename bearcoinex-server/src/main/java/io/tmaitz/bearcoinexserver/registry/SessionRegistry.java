package io.tmaitz.bearcoinexserver.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionRegistry {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper jacksonObjectMapper;

    public void register(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public void unregister(WebSocketSession session) {
        sessions.remove(session.getId());
    }

    @SneakyThrows
    public void sendToAll(TextMessage message) {
        for (WebSocketSession session : sessions.values()) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                log.error("Error while sending message to session {}", session.getId(), e);
            }
        }
    }

    @SneakyThrows
    public void sendToAll(String author, String text) {
        var message = new TextMessage(jacksonObjectMapper.writeValueAsString(new ChatMessage(author, text)));
        sendToAll(message);
    }
}
