package com.charlessun.javaplcadapter.application.manager.impl;

import com.charlessun.javaplcadapter.application.manager.ProducerManager;
import com.charlessun.javaplcadapter.application.strategy.ProducerStrategy;
import com.charlessun.javaplcadapter.adapter.producer.Producer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultProducerManager implements ProducerManager {
    private final List<Producer> producers = new ArrayList<>();
    private final List<ProducerStrategy> strategies;

    public DefaultProducerManager(List<ProducerStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public void registerProducers(Producer... producers) {
        for (Producer producer : producers) {
            System.out.println("[ProducerManager] 註冊生產者: " + producer.getName());
            this.producers.add(producer);
        }
    }

    @Override
    public void startProducers() {
        System.out.println("[ProducerManager] 啟動生產者...");
        for (int i = 0; i < producers.size(); i++) {
            Producer producer = producers.get(i);
            System.out.println(" - 生產者 " + producer.getName() + " 開始生產");
            producer.produce(null); // 模擬
            if (i < strategies.size()) {
                System.out.println("   > 啟動策略: " + strategies.get(i).getClass().getSimpleName());
                strategies.get(i).start(producer.getName());
            }
        }
    }
}
