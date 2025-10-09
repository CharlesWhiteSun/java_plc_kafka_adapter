package com.charlessun.javaplcadapter.application.strategy.impl;

import com.charlessun.javaplcadapter.application.strategy.ConsumerStrategy;
import org.springframework.stereotype.Component;

@Component
public class MockConsumerStrategy implements ConsumerStrategy {
    @Override
    public void start(String consumerName) {
        System.out.println("[MockConsumerStrategy]" + consumerName + "消費策略啟動...");
    }
}
