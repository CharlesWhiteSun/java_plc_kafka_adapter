package com.charlessun.javaplcadapter.application.strategy;

public enum StrategyType {
    WRITE_KAFKA("writeKafkaProcessing"),
    NOOP("noopProcessing");

    public static final String WRITE_KAFKA_BEAN_NAME = "writeKafkaProcessing";
    public static final String NOOP_BEAN_NAME = "noopProcessing";
    private final String beanName;

    StrategyType(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}

