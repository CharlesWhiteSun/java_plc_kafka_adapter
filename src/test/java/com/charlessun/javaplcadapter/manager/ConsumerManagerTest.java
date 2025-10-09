package com.charlessun.javaplcadapter.manager;

import com.charlessun.javaplcadapter.model.ConsumerSpec;
import com.charlessun.javaplcadapter.processor.ProcessingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

/**
 * 測試 ConsumerManager 的主要行為：
 * - 註冊消費者
 * - 啟動與停止控制
 */
class ConsumerManagerTest {

    private ConsumerManager consumerManager;

    @BeforeEach
    void setUp() {
        consumerManager = Mockito.mock(ConsumerManager.class);
    }

    @Test
    @DisplayName("應該能註冊一個 Consumer 並返回唯一 ID")
    void shouldRegisterConsumerAndReturnId() {
        // arrange
        ConsumerSpec mockSpec = ConsumerSpec.builder()
                .topics(List.of("topicX"))
                .groupId("groupA")
                .concurrency(1)
                .build();

        ProcessingStrategy<?> mockStrategy = mock(ProcessingStrategy.class);

        when(consumerManager.register(mockSpec, mockStrategy)).thenReturn("consumer-1");

        // act
        String id = consumerManager.register(mockSpec, mockStrategy);

        // assert
        assertEquals("consumer-1", id);
    }
}
