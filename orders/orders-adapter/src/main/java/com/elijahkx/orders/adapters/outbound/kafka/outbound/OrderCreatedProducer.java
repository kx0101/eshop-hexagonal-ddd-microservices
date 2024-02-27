package com.elijahkx.orders.adapters.outbound.kafka.outbound;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import com.elijahkx.orders.domain.orders.OrderDomain;
import com.elijahkx.orders.outbound.kafka.OrderEventPort;

@Component
public class OrderCreatedProducer implements OrderEventPort {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, OrderDomain> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, OrderDomain> kafkaTemplate(ProducerFactory<String, OrderDomain> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Override
    public void produce(OrderDomain orderDomain) {
        KafkaTemplate<String, OrderDomain> kafkaTemplate = kafkaTemplate(producerFactory());

        kafkaTemplate.send("orderCreated", orderDomain);
    }
}
