package com.charlessun.javaplcadapter.adapter.consumer.impl;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import com.charlessun.javaplcadapter.adapter.consumer.KafkaTopicsConsumerProperties;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;

import jakarta.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PlcConsumer implements Consumer<String, PlcData> {

    private final KafkaTopicsConsumerProperties kafkaTopicsConsumerProperties;

    public PlcConsumer(KafkaTopicsConsumerProperties kafkaTopicsConsumerProperties) {
        this.kafkaTopicsConsumerProperties = kafkaTopicsConsumerProperties;
    }

    @Override
    @KafkaListener(
            topics = "#{kafkaTopicsConsumerProperties.topics}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "plcDataKafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, PlcData> record) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.printf(
                "✅ 收到訊息 (Group=%s, Topic=%s, Time=%s, 電壓: %.2f V, 電流: %.2f A)%n",
                record.key(), record.topic(), sdf.format(new Date(record.timestamp())),
                record.value().getVoltage(), record.value().getCurrent()
        );
    }

    @PostConstruct
    public void printTopics() {
        System.out.println("\n============================================");
        System.out.println("已設定的 topics: " + kafkaTopicsConsumerProperties.getTopics());
        System.out.println("============================================\n");
    }
}
