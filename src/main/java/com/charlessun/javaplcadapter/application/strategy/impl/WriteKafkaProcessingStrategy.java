package com.charlessun.javaplcadapter.application.strategy.impl;

import com.charlessun.javaplcadapter.application.service.PlcDataProcessingService;
import com.charlessun.javaplcadapter.application.strategy.PlcDataProcessingStrategy;
import com.charlessun.javaplcadapter.application.strategy.StrategyType;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service(StrategyType.WRITE_KAFKA_BEAN_NAME)
public class WriteKafkaProcessingStrategy implements PlcDataProcessingStrategy {

    private final PlcDataProcessingService plcDataProcessingService;

    public WriteKafkaProcessingStrategy(PlcDataProcessingService plcDataProcessingService) {
        this.plcDataProcessingService = plcDataProcessingService;
    }

    @Override
    public void process(ConsumerRecord<String, PlcData> record) {
        PlcData plcData = record.value();
        String topicFrom = record.topic();
        String key = record.key();

        plcDataProcessingService.processPlcData(topicFrom, key, plcData);
    }
}
