package com.charlessun.javaplcadapter.adapter.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface Producer {
    String getName();
    void produce(ProducerRecord<String, String> record);
}
