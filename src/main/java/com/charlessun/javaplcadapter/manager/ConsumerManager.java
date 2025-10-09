package com.charlessun.javaplcadapter.manager;

import com.charlessun.javaplcadapter.model.ConsumerSpec;
import com.charlessun.javaplcadapter.processor.ProcessingStrategy;

import java.util.Collection;

public interface ConsumerManager {
    String register(ConsumerSpec spec, ProcessingStrategy<?> strategy);
    void start(String consumerId);
    void stop(String consumerId);
    void startAll();
    void stopAll();
    Collection<String> listRegistered();
    // 可能有 status(consumerId)
}