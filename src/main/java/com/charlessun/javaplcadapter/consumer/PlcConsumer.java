package com.charlessun.javaplcadapter.consumer;

import com.charlessun.javaplcadapter.config.KafkaTopicsProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class PlcConsumer {

    private final KafkaTopicsProperties kafkaTopicsProperties;

    public PlcConsumer(KafkaTopicsProperties kafkaTopicsProperties) {
        this.kafkaTopicsProperties = kafkaTopicsProperties;
    }

    @KafkaListener(
            topics = "#{kafkaTopicsProperties.topics}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(byte[] message) {
        System.out.println("收到 byte[] 訊息: " + Arrays.toString(message));

        // 如果需要轉成 String
        String str = new String(message, StandardCharsets.UTF_8);
        System.out.println("轉換成 String: " + str);
    }

    @PostConstruct
    public void printTopics() {
        System.out.println("已設定的 topics: " + kafkaTopicsProperties.getTopics());
    }
}
