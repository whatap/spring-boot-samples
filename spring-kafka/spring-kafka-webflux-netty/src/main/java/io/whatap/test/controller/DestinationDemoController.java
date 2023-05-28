package io.whatap.test.controller;

import io.whatap.test.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class DestinationDemoController {
    private final static Logger log = LoggerFactory.getLogger(DestinationDemoController.class);
    private final KafkaProducerService kafkaProducerService;

    public DestinationDemoController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/kafka/test")
    public Mono<Void> kafkaTest(@RequestParam String message) {
        return kafkaProducerService.sendMessage(message);
    }
}
