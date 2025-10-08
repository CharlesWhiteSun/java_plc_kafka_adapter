package com.charlessun.javaplcadapter.adapter.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * 定義 PLC Kafka 消費服務的抽象介面。
 * 用來接收與處理 Kafka 來的訊息。
 */
public interface Consumer<K, V> {

    /**
     * 監聽並處理收到的訊息。
     *
     * @param record Kafka ConsumerRecord
     */
    void listen(ConsumerRecord<K, V> record);
}