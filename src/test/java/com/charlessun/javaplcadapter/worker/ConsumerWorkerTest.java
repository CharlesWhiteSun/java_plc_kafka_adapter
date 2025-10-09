package com.charlessun.javaplcadapter.worker;

import com.charlessun.javaplcadapter.kafka.client.KafkaConsumerClient;
import com.charlessun.javaplcadapter.model.MessageRecord;
import com.charlessun.javaplcadapter.processor.ProcessingStrategy;
import com.charlessun.javaplcadapter.worker.impl.ConsumerWorkerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConsumerWorkerTest {

    private KafkaConsumerClient<String> mockKafkaClient;
    private ProcessingStrategy<String> mockStrategy;
    private ConsumerWorker worker;

    @BeforeEach
    void setUp() {
        mockKafkaClient = mock(KafkaConsumerClient.class);
        mockStrategy = mock(ProcessingStrategy.class);
    }

    @Test
    void testWorkerPollsAndProcessesRecords() throws Exception {
        // Arrange: 建立 MessageRecord 列表，補上 partition 與 offset
        List<MessageRecord<String>> records = List.of(
                new MessageRecord<>("topicX", "test-message", 0, 123L)
        );
        when(mockKafkaClient.poll(any(Duration.class))).thenReturn(records);

        worker = new ConsumerWorkerImpl(mockKafkaClient, mockStrategy);

        // Act
        worker.start();
        Thread.sleep(500); // 等 Worker 執行
        worker.stop();

        // Assert
        verify(mockKafkaClient, atLeastOnce()).poll(any(Duration.class));
        verify(mockStrategy, atLeastOnce()).process(records.get(0));
    }

    @Test
    void testWorkerHandlesProcessingExceptionGracefully() throws Exception {
        // Arrange: 建立 MessageRecord 列表，補上 partition 與 offset
        List<MessageRecord<String>> records = List.of(
                new MessageRecord<>("topicX", "error-message", 0, 456L)
        );
        when(mockKafkaClient.poll(any(Duration.class))).thenReturn(records);

        doThrow(new RuntimeException("Processing failed"))
                .when(mockStrategy).process(records.get(0));

        worker = new ConsumerWorkerImpl(mockKafkaClient, mockStrategy);

        // Act
        worker.start();
        Thread.sleep(500);
        worker.stop();

        // Assert
        verify(mockStrategy, atLeastOnce()).process(records.get(0));
        verify(mockKafkaClient, atLeastOnce()).poll(any(Duration.class));
    }
}
