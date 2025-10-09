package com.charlessun.javaplcadapter.worker.impl;

import com.charlessun.javaplcadapter.model.MessageRecord;
import com.charlessun.javaplcadapter.util.LogUtil;
import com.charlessun.javaplcadapter.worker.ConsumerWorker;
import com.charlessun.javaplcadapter.kafka.client.KafkaConsumerClient;
import com.charlessun.javaplcadapter.processor.ProcessingStrategy;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;

public class ConsumerWorkerImpl<T> implements ConsumerWorker {
    private static final Logger log = LogUtil.getLogger(ConsumerWorkerImpl.class);

    private final KafkaConsumerClient<T> kafkaConsumerClient;
    private final ProcessingStrategy<T> processingStrategy;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread workerThread;

    public ConsumerWorkerImpl(KafkaConsumerClient<T> kafkaConsumerClient, ProcessingStrategy<T> processingStrategy) {
        this.kafkaConsumerClient = kafkaConsumerClient;
        this.processingStrategy = processingStrategy;
    }

    @Override
    public void start() {
        running.set(true);
        workerThread = new Thread(this::pollLoop);
        workerThread.start();
    }

    @Override
    public void stop() {
        running.set(false);
        kafkaConsumerClient.wakeup();
        if (workerThread != null) {
            try {
                workerThread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void pollLoop() {
        while (running.get()) {
            try {
                List<MessageRecord<T>> records = kafkaConsumerClient.poll(Duration.ofMillis(100));
                for (MessageRecord<T> record : records) {
                    try {
                        processingStrategy.process(record);
                    } catch (Exception e) {
                        log.error("processingStrategy.process(record) 處理失敗: {}", e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                log.error("pollLoop 處理失敗: {}", e.getMessage(), e);
            }
        }
    }

}
