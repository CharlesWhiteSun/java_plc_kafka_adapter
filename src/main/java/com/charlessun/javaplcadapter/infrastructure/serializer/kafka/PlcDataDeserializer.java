package com.charlessun.javaplcadapter.infrastructure.serializer.kafka;

import com.charlessun.javaplcadapter.domain.model.impl.PlcData;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

public class PlcDataDeserializer implements Deserializer<PlcData> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // 不需要設定
    }

    @Override
    public PlcData deserialize(String topic, byte[] data) {
        if (data == null || data.length < 8) {
            return null;
        }
        try {
            ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN);
            float voltage = buffer.getFloat();
            float current = buffer.getFloat();
            return new PlcData(voltage, current);
        } catch (Exception e) {
            throw new RuntimeException("PlcData 反序列化失敗", e);
        }
    }

    @Override
    public void close() {}
}
