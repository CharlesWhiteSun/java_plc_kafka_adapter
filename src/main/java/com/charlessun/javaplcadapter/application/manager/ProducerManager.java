package com.charlessun.javaplcadapter.application.manager;

import com.charlessun.javaplcadapter.adapter.producer.Producer;

public interface ProducerManager {
    void registerProducers(Producer... producers);
    void startProducers();
}