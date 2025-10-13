package com.charlessun.javaplcadapter.workflow;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class WorkflowContext {
    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public void remove(String key) {
        data.remove(key);
    }

    public void clear() {
        data.clear();
    }
}
