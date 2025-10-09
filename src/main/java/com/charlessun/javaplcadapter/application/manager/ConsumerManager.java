package com.charlessun.javaplcadapter.application.manager;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;

public interface ConsumerManager {
    void registerConsumers(Consumer... consumers);
    void startConsumers();
}
