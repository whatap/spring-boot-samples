package io.whatap.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class KafkaConsumerService {

    private final static Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "exam", groupId = "foo")
    public Mono<Void> consume(String message) throws IOException {
        log.info("Consumed message : ------"+ message);
        return Mono.empty();
    }
}