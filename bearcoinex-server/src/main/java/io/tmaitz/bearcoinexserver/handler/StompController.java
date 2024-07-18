package io.tmaitz.bearcoinexserver.handler;

import io.tmaitz.bearcoinexserver.registry.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompController {

    @MessageMapping("/message")
    @SendTo("/topic/chat")
    public ChatMessage message(ChatMessage message) {
        log.info("Received message: {}", message);
        return message;
    }

}
