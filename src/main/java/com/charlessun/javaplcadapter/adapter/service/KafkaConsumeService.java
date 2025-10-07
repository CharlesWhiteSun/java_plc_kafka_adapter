package com.charlessun.javaplcadapter.adapter.service;

import com.charlessun.javaplcadapter.adapter.config.KafkaTopicsConsumerProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;

@Service
public class KafkaConsumeService {

    @Autowired
    private ConsumerFactory<String, byte[]> consumerFactory;

    @Autowired
    private KafkaTopicsConsumerProperties kafkaTopicsConsumerProperties;

    @Value("${kafka.consume.strategy}")
    private String consumeStrategy;

    @Value("${kafka.consume.interval-ms}")
    private long intervalMs;

    @EventListener(ApplicationReadyEvent.class)
    public void startConsume() {
        new Thread(this::consumeLoop).start();
    }

    private void consumeLoop() {
        KafkaConsumer<String, byte[]> consumer = (KafkaConsumer<String, byte[]>) consumerFactory.createConsumer();
        consumer.subscribe(kafkaTopicsConsumerProperties.getTopics());

        try {
            if ("unlimited".equalsIgnoreCase(consumeStrategy)) {
                unlimitedConsume(consumer);
            } else if ("interval".equalsIgnoreCase(consumeStrategy)) {
                intervalConsume(consumer);
            } else {
                throw new IllegalArgumentException("Unknown consume strategy: " + consumeStrategy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    private void unlimitedConsume(KafkaConsumer<String, byte[]> consumer) {
        System.out.println("\n============================================");
        System.out.println("啟動無限制拉取模式...");
        System.out.println("============================================\n");
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
            processRecords(records);
        }
    }

    private void intervalConsume(KafkaConsumer<String, byte[]> consumer) throws InterruptedException {
        System.out.println("\n============================================");
        System.out.println("啟動指定間隔拉取模式，每 " + intervalMs + " 毫秒拉取一次");
        System.out.println("============================================\n");
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
            processRecords(records);
            Thread.sleep(intervalMs);
        }
    }

    private void processRecords(ConsumerRecords<String, byte[]> records) {
        for (ConsumerRecord<String, byte[]> record : records) {
            System.out.printf("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value(byte[]): %s%n",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    Arrays.toString(record.value())); // 轉 byte[] 為 String，或自行解析
        }
    }
}
