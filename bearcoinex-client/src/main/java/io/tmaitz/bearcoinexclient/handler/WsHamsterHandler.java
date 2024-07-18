package io.tmaitz.bearcoinexclient.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
public class WsHamsterHandler extends TextWebSocketHandler {

    private final List<String> messages = List.of(
            "To The Moon...",
            "Sh*t up and take my money",
            "На всю котлету!",
            "Ждем дивидендики",
            "Ждем bayback"
    );
    private final String name;
    private final ObjectMapper jacksonObjectMapper;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connected");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received Message: {}", message);
        var chatMessage = jacksonObjectMapper.readValue(message.getPayload(), ChatMessage.class);
        if (chatMessage.author().equalsIgnoreCase("Server")) {
            var text = messages.get(ThreadLocalRandom.current().nextInt(messages.size()) % messages.size());
            var chatMessageString = jacksonObjectMapper.writeValueAsString(new ChatMessage(name, text));
            session.sendMessage(new TextMessage(chatMessageString));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Transport Error", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Disconnected with status: {}", status);
    }

}
