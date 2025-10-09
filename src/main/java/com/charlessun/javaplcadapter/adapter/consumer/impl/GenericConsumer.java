package com.charlessun.javaplcadapter.adapter.consumer.impl;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class GenericConsumer implements Consumer {
    private final String name;

    public GenericConsumer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println("[" + name + "] 消費 Kafka 訊息...");
    }
}
