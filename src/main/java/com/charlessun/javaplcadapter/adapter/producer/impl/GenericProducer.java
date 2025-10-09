package com.charlessun.javaplcadapter.adapter.producer.impl;

import com.charlessun.javaplcadapter.adapter.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class GenericProducer implements Producer {
    private final String name;

    public GenericProducer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void produce(ProducerRecord<String, String> record) {
        System.out.println("[" + name + "] 生產 Kafka 訊息...");
    }
}
