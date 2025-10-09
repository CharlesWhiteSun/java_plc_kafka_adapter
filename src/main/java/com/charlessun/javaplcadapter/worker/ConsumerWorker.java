package com.charlessun.javaplcadapter.worker;

import com.charlessun.javaplcadapter.kafka.client.KafkaConsumerClient;
import com.charlessun.javaplcadapter.processor.ProcessingStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumerWorker<T> implements Runnable {
    private final KafkaConsumerClient<T> client;
    private final ProcessingStrategy<T> strategy;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public ConsumerWorker(KafkaConsumerClient<T> client, ProcessingStrategy<T> strategy) {
        this.client = client;
        this.strategy = strategy;
    }

    @Override
    public void run() {

    }
    // run(): poll loop -> 將每筆 record 傳給 strategy.process(...)
    // stop(): running=false; client.wakeup();
}