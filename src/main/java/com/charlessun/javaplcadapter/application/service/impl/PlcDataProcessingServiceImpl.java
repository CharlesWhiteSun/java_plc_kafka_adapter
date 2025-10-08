package com.charlessun.javaplcadapter.application.service.impl;

import com.charlessun.javaplcadapter.adapter.producer.PlcProducer;
import com.charlessun.javaplcadapter.application.service.PlcDataProcessingService;
import com.charlessun.javaplcadapter.domain.model.impl.PlcData;
import org.springframework.stereotype.Service;

@Service("plcDataProcessingService")
public class PlcDataProcessingServiceImpl implements PlcDataProcessingService {

    private final PlcProducer plcProducer;

    public PlcDataProcessingServiceImpl(PlcProducer plcProducer) {
        this.plcProducer = plcProducer;
    }

    @Override
    public void processPlcData(String topicFrom, String key, PlcData plcData) {
        if (plcData == null) return;

        System.out.printf("🔍 處理 PLC 資料: voltage=%.2f, current=%.2f%n",
                plcData.getVoltage(), plcData.getCurrent());

        if (plcData.getVoltage() > 200) {
            String message = String.format(
                    "Group: %s, Topic_From: %s, voltage: %.5f, current: %.5f",
                    key, topicFrom, plcData.getVoltage(), plcData.getCurrent()
            );
            plcProducer.sendToResolvedTopic(topicFrom, key, message);
        }
    }
}
