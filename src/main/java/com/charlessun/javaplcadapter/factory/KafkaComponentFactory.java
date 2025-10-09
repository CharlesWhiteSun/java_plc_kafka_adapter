package com.charlessun.javaplcadapter.factory;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import com.charlessun.javaplcadapter.adapter.consumer.impl.GenericConsumer;
import com.charlessun.javaplcadapter.adapter.producer.Producer;
import com.charlessun.javaplcadapter.adapter.producer.impl.GenericProducer;
import org.springframework.stereotype.Component;

@Component
public class KafkaComponentFactory {

    public Consumer createConsumer(String name) {
        return new GenericConsumer(name);
    }

    public Producer createProducer(String name) {
        return new GenericProducer(name);
    }
}
