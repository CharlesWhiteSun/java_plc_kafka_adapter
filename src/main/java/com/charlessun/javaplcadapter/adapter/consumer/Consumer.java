package com.charlessun.javaplcadapter.adapter.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface Consumer {
    String getName();
    void consume(ConsumerRecord<String, String> record);
}
