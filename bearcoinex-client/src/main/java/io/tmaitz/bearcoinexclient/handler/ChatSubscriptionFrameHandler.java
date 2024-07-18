package io.tmaitz.bearcoinexclient.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
public class ChatSubscriptionFrameHandler implements StompFrameHandler {

    private final List<String> messages = List.of(
            "To The Moon...",
            "Sh*t up and take my money",
            "На всю котлету!",
            "Ждем дивидендики",
            "Ждем bayback"
    );

    private final String name;
    private final ObjectMapper jacksonObjectMapper;
    private final StompSession stompSession;

    @SneakyThrows
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        var chatMessage = jacksonObjectMapper.readValue((byte []) payload, ChatMessage.class);
        log.info("Handling message: {}, headers: {}", chatMessage, headers);
        if (chatMessage.author().equalsIgnoreCase("Server")) {
            var text = messages.get(ThreadLocalRandom.current().nextInt(messages.size()) % messages.size());
            stompSession.send("/app/message", jacksonObjectMapper.writeValueAsBytes(new ChatMessage(name, text)));
        }
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return byte[].class;
    }
}
