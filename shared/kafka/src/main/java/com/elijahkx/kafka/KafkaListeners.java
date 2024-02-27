package com.elijahkx.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "liakosTopic", groupId = "liakos")
    void consume(String data) {
        System.out.println("Consume for liakosTopic: " + data);
    }
}
