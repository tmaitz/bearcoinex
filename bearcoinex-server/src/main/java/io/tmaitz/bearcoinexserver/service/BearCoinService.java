package io.tmaitz.bearcoinexserver.service;

import io.tmaitz.bearcoinexserver.registry.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class BearCoinService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private Double price = 100.0;

    @Scheduled(fixedRate = 5_000)
    public void askClients() {
        simpMessagingTemplate.convertAndSend("/topic/chat", new ChatMessage("Server", "Поделитесь своим мнением"));
    }

    @Scheduled(fixedRate = 1_000)
    public void publishPrice() {
        price += (Math.random() - 0.4) * 10;
        simpMessagingTemplate.convertAndSend("/topic/price", price);
    }

}
