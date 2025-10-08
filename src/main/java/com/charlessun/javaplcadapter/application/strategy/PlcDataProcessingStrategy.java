package com.charlessun.javaplcadapter.application.strategy;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;

public interface PlcDataProcessingStrategy {
    void process(ConsumerRecord<String, PlcData> record);
}
