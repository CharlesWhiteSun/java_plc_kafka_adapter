package com.charlessun.javaplcadapter.kafka.impl;

import com.charlessun.javaplcadapter.kafka.client.KafkaProducerClient;
import org.springframework.kafka.core.KafkaTemplate;

public class SpringKafkaProducerClient<T> implements KafkaProducerClient<T> {
    private final KafkaTemplate<String, T> kafkaTemplate;

    public SpringKafkaProducerClient(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, T message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    public void close() {
        // KafkaTemplate 沒有 close 方法，可空實作
    }
}
