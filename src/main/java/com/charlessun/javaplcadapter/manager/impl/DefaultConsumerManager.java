package com.charlessun.javaplcadapter.manager.impl;

import com.charlessun.javaplcadapter.manager.ConsumerManager;
import com.charlessun.javaplcadapter.model.ConsumerSpec;
import com.charlessun.javaplcadapter.processor.ProcessingStrategy;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 預設的 ConsumerManager 實作。
 */
public class DefaultConsumerManager implements ConsumerManager {

    private final Map<String, ConsumerSpec> registeredConsumers = new ConcurrentHashMap<>();
    private final Map<String, ProcessingStrategy<?>> strategies = new ConcurrentHashMap<>();
    private final Map<String, Thread> runningConsumers = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public String register(ConsumerSpec spec, ProcessingStrategy<?> strategy) {
        String consumerId = "consumer-" + idCounter.incrementAndGet();
        registeredConsumers.put(consumerId, spec);
        strategies.put(consumerId, strategy); // 保存策略
        return consumerId;
    }

    @Override
    public void start(String consumerId) {
        ConsumerSpec spec = registeredConsumers.get(consumerId);
        ProcessingStrategy<?> strategy = strategies.get(consumerId);

        if (spec != null && strategy != null && !runningConsumers.containsKey(consumerId)) {
            Thread workerThread = new Thread(() -> {
                System.out.println("Consumer " + consumerId + " running...");
                // TODO: 這裡會啟動 ConsumerWorker 處理邏輯
            });
            workerThread.start();
            runningConsumers.put(consumerId, workerThread);
        }
    }

    @Override
    public void stop(String consumerId) {
        Thread workerThread = runningConsumers.get(consumerId);
        if (workerThread != null && workerThread.isAlive()) {
            workerThread.interrupt();
            runningConsumers.remove(consumerId);
        }
    }

    @Override
    public void startAll() {
        for (String consumerId : registeredConsumers.keySet()) {
            start(consumerId);
        }
    }

    @Override
    public void stopAll() {
        for (String consumerId : runningConsumers.keySet()) {
            stop(consumerId);
        }
    }

    @Override
    public Collection<String> listRegistered() {
        return registeredConsumers.keySet();
    }
}
