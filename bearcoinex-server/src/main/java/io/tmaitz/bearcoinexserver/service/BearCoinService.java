package io.tmaitz.bearcoinexserver.service;

import io.tmaitz.bearcoinexserver.registry.SessionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BearCoinService {

    private final SessionRegistry sessionRegistry;

    @Scheduled(fixedRate = 5_000)
    public void askClients() {
        sessionRegistry.sendToAll("Server", "Поделитесь своим мнением");
    }

}
