package io.tmaitz.bearcoinexclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tmaitz.bearcoinexclient.handler.WsHamsterHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
@RequiredArgsConstructor
public class HamsterService {

    private final ObjectMapper jacksonObjectMapper;
    @Value("${bearcoinex.ws.url:ws://localhost:8080/ws-chat}")
    private String bearcoinexWsUrl;
    @Value("${hamster.size:1}")
    private int hamsterSize;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void initClients() {
        for (int i = 0; i < hamsterSize; i++) {
            var client = new StandardWebSocketClient();
            var webSocketHandler = new WsHamsterHandler("Client-" + i, jacksonObjectMapper);
            client.execute(webSocketHandler, bearcoinexWsUrl);
        }
    }

}
