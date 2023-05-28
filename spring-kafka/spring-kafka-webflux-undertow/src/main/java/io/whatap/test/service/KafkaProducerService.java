package io.whatap.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KafkaProducerService {
    private final static Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    private static final String TOPIC = "exam";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> sendMessage(String message) {
        System.out.println(String.format("Produce message : %s", message));
         kafkaTemplate.send(TOPIC, message);
        return Mono.empty();
    }


}