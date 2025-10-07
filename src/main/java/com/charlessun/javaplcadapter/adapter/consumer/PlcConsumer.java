package com.charlessun.javaplcadapter.adapter.consumer;

import com.charlessun.javaplcadapter.adapter.config.KafkaTopicsConsumerProperties;
import com.charlessun.javaplcadapter.adapter.producer.PlcProducer;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PlcConsumer {

    private final KafkaTopicsConsumerProperties KafkaTopicsConsumerProperties;
    private final PlcProducer plcProducer;

    public PlcConsumer(KafkaTopicsConsumerProperties kafkaTopicsConsumerProperties, PlcProducer plcProducer) {
        this.KafkaTopicsConsumerProperties = kafkaTopicsConsumerProperties;
        this.plcProducer = plcProducer;
    }

    @KafkaListener(
            topics = "#{kafkaTopicsConsumerProperties.topics}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "plcDataKafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, PlcData> record) {
        String key = record.key();      // Kafka 的 Key (Go 那邊設的 group)
        String topicFrom = record.topic();
        PlcData plcData = record.value();

        System.out.printf(
                "✅ 收到訊息 (Group=%s, Topic=%s, Partition=%d, Offset=%d)%n",
                key, record.topic(), record.partition(), record.offset()
        );

        System.out.printf("➡️ 解析後物件: %s%n", plcData);
        System.out.printf("電壓: %.2f V, 電流: %.2f A%n",
                plcData.getVoltage(), plcData.getCurrent());

        // 把資料送到對應的 resolved topic
        String message = String.format("Group: %s, Topic_From: %s, voltage: %.5f, current: %.5f",
                key, topicFrom, plcData.getVoltage(), plcData.getCurrent());

        plcProducer.sendToResolvedTopic(topicFrom, key, message);
    }

    @PostConstruct
    public void printTopics() {
        System.out.println("\n============================================");
        System.out.println("已設定的 topics: " + KafkaTopicsConsumerProperties.getTopics());
        System.out.println("============================================\n");
    }
}
