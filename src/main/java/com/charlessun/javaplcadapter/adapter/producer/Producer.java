package com.charlessun.javaplcadapter.adapter.producer;

/**
 * 定義通用的 Kafka Producer 介面。
 */
public interface Producer<K, V> {

    /**
     * 發送訊息到指定的 topic。
     *
     * @param key   Kafka 訊息 key
     * @param value Kafka 訊息內容
     */
    void send(K key, V value);
}
