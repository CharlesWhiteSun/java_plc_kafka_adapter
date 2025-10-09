package com.charlessun.javaplcadapter;

import com.charlessun.javaplcadapter.application.manager.ConsumerManager;
import com.charlessun.javaplcadapter.application.manager.ProducerManager;
import com.charlessun.javaplcadapter.factory.KafkaComponentFactory;
import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import com.charlessun.javaplcadapter.adapter.producer.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);

        ConsumerManager consumerManager = context.getBean(ConsumerManager.class);
        ProducerManager producerManager = context.getBean(ProducerManager.class);
        KafkaComponentFactory factory = context.getBean(KafkaComponentFactory.class);

        System.out.println("=== Kafka Adapter 啟動流程驗證（工廠模式） ===");

        // 註冊消費者 A, B, C
        Consumer consumerA = factory.createConsumer("ConsumerA");
        Consumer consumerB = factory.createConsumer("ConsumerB");
        Consumer consumerC = factory.createConsumer("ConsumerC");
        consumerManager.registerConsumers(consumerA, consumerB, consumerC);

        // 啟動消費者
        consumerManager.startConsumers();

        // 註冊生產者 D, E, F
        Producer producerD = factory.createProducer("ProducerD");
        Producer producerE = factory.createProducer("ProducerE");
        Producer producerF = factory.createProducer("ProducerF");
        producerManager.registerProducers(producerD, producerE, producerF);

        // 啟動生產者
        producerManager.startProducers();

        System.out.println("=== 啟動完成 ===");
    }
}
