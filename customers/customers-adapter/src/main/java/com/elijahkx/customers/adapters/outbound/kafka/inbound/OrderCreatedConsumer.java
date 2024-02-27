package com.elijahkx.customers.adapters.outbound.kafka.inbound;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    @KafkaListener(topics = "orderCreated", groupId = "liakos")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
