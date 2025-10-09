package com.charlessun.javaplcadapter.kafka.client;

import com.charlessun.javaplcadapter.model.MessageRecord;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

public interface KafkaConsumerClient<T> {
    void subscribe(Collection<String> topics);
    List<MessageRecord<T>> poll(Duration timeout);
    void close();
    void wakeup(); // 用於快速終止 blocking poll
}