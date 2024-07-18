package io.tmaitz.bearcoinexclient.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
@RequiredArgsConstructor
public class WsHamsterHandler extends StompSessionHandlerAdapter {

    private final String name;
    private final ObjectMapper jacksonObjectMapper;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("Connected");
        session.subscribe("/topic/chat", new ChatSubscriptionFrameHandler(name, jacksonObjectMapper, session));
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Exception for sessionId: {}, command: {}, headers: {}", session.getSessionId(), command.name(), headers, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error("Transport Error for sessionId: {}", session.getSessionId(), exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return byte[].class;
    }

}
