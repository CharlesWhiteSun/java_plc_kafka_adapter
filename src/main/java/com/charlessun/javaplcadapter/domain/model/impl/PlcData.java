package com.charlessun.javaplcadapter.domain.model.impl;

import com.charlessun.javaplcadapter.domain.model.BaseEntity;

public class PlcData implements BaseEntity {
    private final float voltage;
    private final float current;

    public PlcData(float voltage, float current) {
        this.voltage = voltage;
        this.current = current;
    }

    public float getVoltage() {
        return voltage;
    }

    public float getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        return "PlcData{voltage=" + voltage + ", current=" + current + "}";
    }
}
