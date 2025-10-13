package com.charlessun.javaplcadapter.workflow.impl;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import com.charlessun.javaplcadapter.workflow.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsumerTask implements Task {
    private final String name;
    private final Consumer consumer;
    private final ConsumerRecord<String, String> record;

    public ConsumerTask(String name, Consumer consumer, ConsumerRecord<String, String> record) {
        this.name = name;
        this.consumer = consumer;
        this.record = record;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TaskResult execute(WorkflowContext context) {
        System.out.println("   ▶ Executing ConsumerTask: " + name);
        try {
            consumer.consume(record);
            return DefaultTaskResult.success();
        } catch (Exception e) {
            return DefaultTaskResult.failure(e);
        }
    }
}
