package io.tmaitz.bearcoinexserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BearcoinexServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BearcoinexServerApplication.class, args);
    }

}
