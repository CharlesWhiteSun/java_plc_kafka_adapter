package com.charlessun.javaplcadapter.integration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestKafkaConsumer {

    private final List<ConsumerRecord<String, String>> consumedRecords = new CopyOnWriteArrayList<>();

    public TestKafkaConsumer(EmbeddedKafkaBroker broker) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker.getBrokersAsString());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(props);
        ContainerProperties containerProps = new ContainerProperties("test-topic");

        containerProps.setMessageListener((MessageListener<String, String>) record -> {
            consumedRecords.add(record);
        });

        KafkaMessageListenerContainer<String, String> container =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container.start();
    }

    public List<ConsumerRecord<String, String>> getConsumedRecords() {
        return consumedRecords;
    }
}
