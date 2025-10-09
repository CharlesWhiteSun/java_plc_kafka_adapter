package com.charlessun.javaplcadapter.kafka.client;

import java.util.Collection;

public interface KafkaProducerClient<T> {
    void send(String topic, T message);
    void close();
}
