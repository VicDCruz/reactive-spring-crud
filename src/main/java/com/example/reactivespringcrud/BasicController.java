package com.example.reactivespringcrud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api")
@Slf4j
public class BasicController {
    @GetMapping("/demo")
    public Mono<String> greetingsMessage() {
        Mono<String> message = computeMessage().zipWith(getNameFromDB())
                .map(objects -> objects.getT1() + " " + objects.getT2());
        log.info("Message is processing");
        return message;
    }

    private Mono<String> computeMessage() {
        log.info("Start computation");
        return Mono.just("Hello,").delayElement(Duration.ofSeconds(4));
    }

    private Mono<String> getNameFromDB() {
        log.info("Start getNameFromDB");
        return Mono.just("Victor").delayElement(Duration.ofSeconds(5));
    }
}
