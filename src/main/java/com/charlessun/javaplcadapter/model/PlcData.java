package com.charlessun.javaplcadapter.model;

public class PlcData {
    private float voltage;
    private float current;

    public PlcData() {}

    public PlcData(float voltage, float current) {
        this.voltage = voltage;
        this.current = current;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return String.format("PlcData{voltage=%.2f V, current=%.2f A}", voltage, current);
    }
}
