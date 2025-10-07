package com.charlessun.javaplcadapter.adapter.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlcProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicsProducerProperties producerProperties;

    public PlcProducer(KafkaTemplate<String, String> kafkaTemplate,
                       KafkaTopicsProducerProperties producerProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.producerProperties = producerProperties;
    }

    public void send(String topicName, String key, String message) {
        kafkaTemplate.send(topicName, key, message);
        System.out.printf("📤 發送訊息到 Topic=%s, Key=%s, Message=%s%n", topicName, key, message);
    }

    public void sendToResolvedTopic(String originalTopic, String key, String message) {
        if (producerProperties.getTopics() == null || producerProperties.getTopics().isEmpty()) {
            System.err.println("⚠️ Producer topics 尚未載入，請檢查 application.yml 設定格式。");
            return;
        }

        String resolvedTopic = producerProperties.getTopics().get(originalTopic);
        if (resolvedTopic != null) {
            send(resolvedTopic, key, message);
        } else {
            System.err.printf("⚠️ 無對應的 resolved topic: %s%n", originalTopic);
        }
    }
}
