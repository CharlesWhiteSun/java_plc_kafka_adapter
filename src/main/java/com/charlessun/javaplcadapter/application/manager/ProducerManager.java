package com.charlessun.javaplcadapter.application.manager;

import com.charlessun.javaplcadapter.adapter.producer.Producer;

/**
 * Producer 管理器介面，負責統一管理多個 Producer 的啟動與發送。
 */
public interface ProducerManager {

    /**
     * 啟動並註冊所有 Producer。
     *
     * @param producers 可變參數，傳入多個 Producer。
     */
    void Start(Producer<?, ?>... producers);

    /**
     * 發送訊息給所有已註冊的 Producer。
     *
     * @param key   訊息 key
     * @param value 訊息內容
     */
    <K, V> void Broadcast(K key, V value);
}
