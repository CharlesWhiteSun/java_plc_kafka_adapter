package com.charlessun.javaplcadapter.application.manager.impl;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import com.charlessun.javaplcadapter.application.manager.ConsumerManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 預設的 Consumer 管理器實作。
 *
 * 注意：Consumer 本身的監聽是由 Spring Kafka 啟動的，
 * 此 Manager 主要負責統一啟動流程、檢查配置或執行初始化。
 */
@Service
public class DefaultConsumerManager implements ConsumerManager {

    @Override
    public void Start(Consumer<?, ?>... consumers) {
        if (consumers == null || consumers.length == 0) {
            System.out.println("⚪ 沒有可啟動的 Consumer。");
            return;
        }

        System.out.println("🚀 啟動 Consumer 服務:");
        Arrays.stream(consumers).forEach(consumer ->
                System.out.printf(" - ✅ 已註冊 Consumer: %s%n", consumer.getClass().getSimpleName())
        );
    }
}
