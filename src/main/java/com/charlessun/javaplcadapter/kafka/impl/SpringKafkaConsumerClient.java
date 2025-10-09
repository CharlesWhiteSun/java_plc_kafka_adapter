package com.charlessun.javaplcadapter.kafka.impl;

import com.charlessun.javaplcadapter.kafka.client.KafkaConsumerClient;
import com.charlessun.javaplcadapter.model.MessageRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.ConsumerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SpringKafkaConsumerClient<T> implements KafkaConsumerClient<T> {
    private final KafkaConsumer<String, T> consumer;

    public SpringKafkaConsumerClient(ConsumerFactory<String, T> consumerFactory) {
        this.consumer = (KafkaConsumer<String, T>) consumerFactory.createConsumer();
    }

    @Override
    public void subscribe(Collection<String> topics) {
        consumer.subscribe(topics);
    }

    @Override
    public List<MessageRecord<T>> poll(Duration timeout) {
        ConsumerRecords<String, T> records = consumer.poll(timeout);

        List<MessageRecord<T>> result = new ArrayList<>();
        for (ConsumerRecord<String, T> record : records) {
            result.add(new MessageRecord<>(
                    record.topic(),
                    record.value(),
                    record.partition(),
                    record.offset()
            ));
        }
        return result;
    }

    @Override
    public void close() {
        consumer.close();
    }

    @Override
    public void wakeup() {
        consumer.wakeup();
    }
}
