package com.charlessun.javaplcadapter.integration;

import com.charlessun.javaplcadapter.kafka.client.FakeKafkaConsumerClient;
import com.charlessun.javaplcadapter.model.MessageRecord;
import com.charlessun.javaplcadapter.processor.ProcessingStrategy;
import com.charlessun.javaplcadapter.worker.ConsumerWorker;
import com.charlessun.javaplcadapter.worker.impl.ConsumerWorkerImpl;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsumerIntegrationTest {

    @Test
    void testMultipleConsumersProcessing() throws InterruptedException {
        int consumerCount = 3;
        int messageCountPerConsumer = 5;
        CountDownLatch latch = new CountDownLatch(consumerCount * messageCountPerConsumer);
        AtomicInteger processedMessages = new AtomicInteger(0);

        // 建立 Fake Kafka Clients 與 Strategies
        FakeKafkaConsumerClient<String>[] clients = new FakeKafkaConsumerClient[consumerCount];
        ConsumerWorker[] workers = new ConsumerWorker[consumerCount];

        for (int i = 0; i < consumerCount; i++) {
            clients[i] = new FakeKafkaConsumerClient<>();
            ProcessingStrategy<String> strategy = record -> {
                processedMessages.incrementAndGet();
                latch.countDown();
            };
            workers[i] = new ConsumerWorkerImpl<>(clients[i], strategy);
        }

        // 啟動 ConsumerWorkers
        for (ConsumerWorker worker : workers) {
            worker.start();
        }

        // 模擬 Kafka 消息
        for (int i = 0; i < consumerCount; i++) {
            for (int j = 0; j < messageCountPerConsumer; j++) {
                clients[i].addRecord(new MessageRecord<>("topic-test", "msg-" + i + "-" + j, 0, j));
            }
        }

        // 等待全部處理完成
        latch.await();

        // 停止 ConsumerWorkers
        for (ConsumerWorker worker : workers) {
            worker.stop();
        }

        assertEquals(consumerCount * messageCountPerConsumer, processedMessages.get());
    }
}
