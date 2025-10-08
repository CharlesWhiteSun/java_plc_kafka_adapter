package com.charlessun.javaplcadapter.application.strategy.impl;

import com.charlessun.javaplcadapter.application.strategy.PlcDataProcessingStrategy;
import com.charlessun.javaplcadapter.application.strategy.StrategyType;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service(StrategyType.NOOP_BEAN_NAME)
public class NoopProcessingStrategy implements PlcDataProcessingStrategy {

    @Override
    public void process(ConsumerRecord<String, PlcData> record) {
        // 不做任何事情
        System.out.println("⚪ NoopProcessingStrategy: 收到訊息，但不進行任何處理。");
    }
}
