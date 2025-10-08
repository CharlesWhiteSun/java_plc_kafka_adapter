package com.charlessun.javaplcadapter.application.manager;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import com.charlessun.javaplcadapter.application.service.ServiceManager;

/**
 * Consumer 管理器介面，負責管理多個 Consumer 的啟動與關閉。
 */
public interface ConsumerManager {

    /**
     * 啟動多個 Consumer 實例。
     *
     * @param consumers 可變參數，傳入多個 Consumer。
     */
    void Start(Consumer<?, ?>... consumers);
}