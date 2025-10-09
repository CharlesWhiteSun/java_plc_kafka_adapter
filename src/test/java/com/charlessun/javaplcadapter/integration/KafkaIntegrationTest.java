package com.charlessun.javaplcadapter.integration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = TestKafkaConfig.class)
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
public class KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TestKafkaConsumer consumer;

    @Test
    void testSendAndConsumeMessage() throws Exception {
        String message = "Hello Kafka!";
        kafkaTemplate.send("test-topic", message);

        // 等待消費者接收
        Thread.sleep(1000);

        List<ConsumerRecord<String, String>> records = consumer.getConsumedRecords();
        assertThat(records)
                .isNotEmpty()
                .anyMatch(record -> message.equals(record.value()));
    }
}
