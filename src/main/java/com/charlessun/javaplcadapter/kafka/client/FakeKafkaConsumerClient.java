package com.charlessun.javaplcadapter.kafka.client;

import com.charlessun.javaplcadapter.model.MessageRecord;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FakeKafkaConsumerClient<T> implements KafkaConsumerClient<T> {

    private final BlockingQueue<MessageRecord<T>> queue = new LinkedBlockingQueue<>();
    private final Set<String> subscribedTopics = new HashSet<>();
    private volatile boolean closed = false;

    @Override
    public void subscribe(Collection<String> topics) {
        subscribedTopics.addAll(topics);
    }

    @Override
    public List<MessageRecord<T>> poll(Duration timeout) {
        List<MessageRecord<T>> records = new ArrayList<>();
        try {
            MessageRecord<T> record = queue.poll(timeout.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
            if (record != null) {
                records.add(record);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return records;
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public void wakeup() {
        // no-op for fake
    }

    public void addRecord(MessageRecord<T> record) {
        if (!closed) {
            queue.offer(record);
        }
    }
}
