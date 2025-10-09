package com.charlessun.javaplcadapter.processor;

import com.charlessun.javaplcadapter.model.MessageRecord;

/**
 * 處理消費到的訊息策略介面。
 * 不同實作可注入不同後續邏輯（例如寫入 DB、轉發 Topic、記錄日誌等）。
 */
public interface ProcessingStrategy<T> {
    void process(MessageRecord<T> record) throws Exception;
}