package com.charlessun.javaplcadapter.application.service;

import com.charlessun.javaplcadapter.domain.model.impl.PlcData;

public interface PlcDataProcessingService {

    /**
     * 處理接收到的 PLC 資料
     *
     * @param topicFrom  訊息來源的 Topic 名稱
     * @param key        Kafka 訊息 Key
     * @param plcData    PLC 資料物件
     */
    void processPlcData(String topicFrom, String key, PlcData plcData);
}
