package com.charlessun.javaplcadapter.model;

import java.util.List;

/**
 * 用於註冊 Consumer 的基本設定。
 */
public class ConsumerSpec {

    private List<String> topics;
    private String groupId;
    private int concurrency = 1; // 預設併發數 1

    public ConsumerSpec(List<String> topics, String groupId, int concurrency) {
        this.topics = topics;
        this.groupId = groupId;
        this.concurrency = concurrency;
    }

    public List<String> getTopics() {
        return topics;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> topics;
        private String groupId;
        private int concurrency = 1;

        public Builder topics(List<String> topics) {
            this.topics = topics;
            return this;
        }

        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder concurrency(int concurrency) {
            this.concurrency = concurrency;
            return this;
        }

        public ConsumerSpec build() {
            return new ConsumerSpec(topics, groupId, concurrency);
        }
    }
}
