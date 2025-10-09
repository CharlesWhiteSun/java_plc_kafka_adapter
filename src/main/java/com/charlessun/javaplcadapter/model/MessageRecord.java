package com.charlessun.javaplcadapter.model;

public final class MessageRecord<T> {
    private final String topic;
    private final T value;
    private final int partition;
    private final long offset;

    public MessageRecord(String topic, T value, int partition, long offset) {
        this.topic = topic;
        this.value = value;
        this.partition = partition;
        this.offset = offset;
    }

    public String getTopic() {
        return topic;
    }

    public T getValue() {
        return value;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "MessageRecord{" +
                "topic='" + topic + '\'' +
                ", value=" + value +
                ", partition=" + partition +
                ", offset=" + offset +
                '}';
    }
}
