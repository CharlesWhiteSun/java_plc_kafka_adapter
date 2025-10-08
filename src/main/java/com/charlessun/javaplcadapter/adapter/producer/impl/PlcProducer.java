package com.charlessun.javaplcadapter.adapter.producer.impl;

import com.charlessun.javaplcadapter.adapter.producer.Producer;
import com.charlessun.javaplcadapter.adapter.producer.KafkaTopicsProducerProperties;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 針對 PLC 數據的 Kafka Producer。
 */
@Component
public class PlcProducer implements Producer<String, PlcData> {

    private final KafkaTemplate<String, PlcData> kafkaTemplate;
    private final KafkaTopicsProducerProperties kafkaTopicsProducerProperties;

    public PlcProducer(
            KafkaTemplate<String, PlcData> kafkaTemplate,
            KafkaTopicsProducerProperties kafkaTopicsProducerProperties
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicsProducerProperties = kafkaTopicsProducerProperties;
    }

    @Override
    public void send(String key, PlcData value) {
        String topic = kafkaTopicsProducerProperties.getTopics().get(key);
        if (topic == null) {
            System.err.printf("⚠️ 找不到對應的 Kafka topic，key=%s%n", key);
            return;
        }
        kafkaTemplate.send(topic, key, value);
        System.out.printf("📤 已發送訊息到 Topic=%s, Key=%s, 電壓=%.2f, 電流=%.2f%n",
                topic, key, value.getVoltage(), value.getCurrent());
    }

}
