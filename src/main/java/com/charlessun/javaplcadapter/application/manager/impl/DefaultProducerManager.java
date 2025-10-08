package com.charlessun.javaplcadapter.application.manager.impl;

import com.charlessun.javaplcadapter.adapter.producer.Producer;
import com.charlessun.javaplcadapter.application.manager.ProducerManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 預設的 Producer 管理器實作。
 * 負責統一管理多個 Producer 的發送行為。
 */
@Service
public class DefaultProducerManager implements ProducerManager {

    private final List<Producer<?, ?>> registeredProducers = new ArrayList<>();

    @Override
    public void Start(Producer<?, ?>... producers) {
        if (producers == null || producers.length == 0) {
            System.out.println("⚪ 沒有可啟動的 Producer。");
            return;
        }

        registeredProducers.addAll(Arrays.asList(producers));
        System.out.println("🚀 啟動 Producer 服務...");
        registeredProducers.forEach(p ->
                System.out.printf("✅ 已註冊 Producer: %s%n", p.getClass().getSimpleName())
        );
    }

    @Override
    public <K, V> void Broadcast(K key, V value) {
        if (registeredProducers.isEmpty()) {
            System.out.println("⚪ 尚未註冊任何 Producer。");
            return;
        }

        System.out.printf("📤 廣播訊息 (key=%s, value=%s) 給所有 Producer...%n", key, value);
        registeredProducers.forEach(p -> {
            try {
                // 安全轉型呼叫
                @SuppressWarnings("unchecked")
                Producer<K, V> producer = (Producer<K, V>) p;
                producer.send(key, value);
            } catch (ClassCastException e) {
                System.err.printf("⚠️ Producer %s 類型不匹配，略過%n", p.getClass().getSimpleName());
            }
        });
    }
}
