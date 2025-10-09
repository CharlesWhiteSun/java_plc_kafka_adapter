package com.charlessun.javaplcadapter.application.strategy.impl;

import com.charlessun.javaplcadapter.application.strategy.ProducerStrategy;
import org.springframework.stereotype.Component;

@Component
public class MockProducerStrategy implements ProducerStrategy {
    @Override
    public void start(String producerName) {
        System.out.println("[MockProducerStrategy]" + producerName + "生產策略啟動...");
    }
}
