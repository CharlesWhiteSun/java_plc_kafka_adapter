package com.charlessun.javaplcadapter.application.service;

/**
 * 定義一個通用服務管理介面，可管理任意數量的可啟動服務。
 * @param <T> 代表要管理的服務型別
 */
public interface ServiceManager<T> {

    /**
     * 啟動指定服務。
     *
     * @param services 一個或多個服務實例
     */
    void Start(T... services);
}
