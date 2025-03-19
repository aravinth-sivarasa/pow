package io.pow.kafka;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class KafkaController {
    
    static final String TOPIC = "pow-topic";

    final Logger log = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("produce-msg")
    public ResponseEntity<Boolean> produceMessage(@RequestBody String msg) {
        log.info("received message: {}", msg);
        var kafkaSend = kafkaTemplate.send(TOPIC, msg);
        return ResponseEntity.accepted().body(kafkaSend.isDone());
    }
    @KafkaListener(topics = TOPIC,groupId = "pow-group")
    public void consumeMessage(String msg) {
        log.info("processed message: {}", msg);
    }

    public record KafkaMessage(String messageId) {
        public KafkaMessage() {
            this(UUID.randomUUID().toString());
        }
        public KafkaMessage(String messageId) {
            this.messageId = messageId;
        }
    }

}
