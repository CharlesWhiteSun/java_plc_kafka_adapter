package com.charlessun.javaplcadapter.adapter.consumer;

import com.charlessun.javaplcadapter.application.strategy.StrategyType;
import com.charlessun.javaplcadapter.application.strategy.PlcDataProcessingStrategy;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlcConsumer {

    private final KafkaTopicsConsumerProperties kafkaTopicsConsumerProperties;
    private final Map<String, PlcDataProcessingStrategy> strategies;

    public PlcConsumer(KafkaTopicsConsumerProperties kafkaTopicsConsumerProperties,
                       Map<String, PlcDataProcessingStrategy> strategies) {
        this.kafkaTopicsConsumerProperties = kafkaTopicsConsumerProperties;
        this.strategies = strategies;
    }

    /**
     * KafkaListener 使用的預設 listen 方法
     */
    @KafkaListener(
            topics = "#{kafkaTopicsConsumerProperties.topics}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "plcDataKafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, PlcData> record) {
        listen(record, getDefaultStrategy());
    }

    /**
     * 可指定策略的 listen 方法
     */
    public void listen(ConsumerRecord<String, PlcData> record, PlcDataProcessingStrategy strategy) {
        String key = record.key();
        String topicFrom = record.topic();
        PlcData plcData = record.value();

        System.out.printf(
                "✅ 收到訊息 (Group=%s, Topic=%s, Partition=%d, Offset=%d, 電壓: %.2f V, 電流: %.2f A)%n",
                key, topicFrom, record.partition(), record.offset(), plcData.getVoltage(), plcData.getCurrent()
        );

        strategy.process(record);
    }

    /**
     * 預設策略取得
     */
    private PlcDataProcessingStrategy getDefaultStrategy() {
        return strategies.getOrDefault(
                StrategyType.WRITE_KAFKA.getBeanName(),
                strategies.getOrDefault(
                        StrategyType.NOOP.getBeanName(),
                        record -> System.out.println("⚪ 無策略可用，跳過處理")
                )
        );
    }

    @PostConstruct
    public void printTopics() {
        System.out.println("\n============================================");
        System.out.println("已設定的 topics: " + kafkaTopicsConsumerProperties.getTopics());
        System.out.println("============================================\n");
    }
}
