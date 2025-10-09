package com.charlessun.javaplcadapter.application.manager.impl;

import com.charlessun.javaplcadapter.application.manager.ConsumerManager;
import com.charlessun.javaplcadapter.application.strategy.ConsumerStrategy;
import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultConsumerManager implements ConsumerManager {
    private final List<Consumer> consumers = new ArrayList<>();
    private final List<ConsumerStrategy> strategies;

    public DefaultConsumerManager(List<ConsumerStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public void registerConsumers(Consumer... consumers) {
        for (Consumer consumer : consumers) {
            System.out.println("[ConsumerManager] 註冊消費者: " + consumer.getName());
            this.consumers.add(consumer);
        }
    }

    @Override
    public void startConsumers() {
        System.out.println("[ConsumerManager] 啟動消費者...");
        for (int i = 0; i < consumers.size(); i++) {
            Consumer consumer = consumers.get(i);
            System.out.println(" - 消費者 " + consumer.getName() + " 開始消費");
            consumer.consume(null); // 模擬
            if (i < strategies.size()) {
                System.out.println("   > 啟動策略: " + strategies.get(i).getClass().getSimpleName());
                strategies.get(i).start(consumer.getName());
            }
        }
    }
}
