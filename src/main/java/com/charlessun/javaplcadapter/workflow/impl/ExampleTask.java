package com.charlessun.javaplcadapter.workflow.impl;

import com.charlessun.javaplcadapter.workflow.*;

public class ExampleTask implements Task {
    private final String name;

    public ExampleTask(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TaskResult execute(WorkflowContext context) throws Exception {
        System.out.println("   ▶ Running task: " + name);

        // 模擬資料傳遞
        if ("FetchData".equals(name)) {
            context.put("data", "PLC raw signal");
        } else if ("ProcessData".equals(name)) {
            String data = context.get("data");
            context.put("processed", data + " → processed");
        } else if ("SaveData".equals(name)) {
            System.out.println("      ✅ Final data: " + context.get("processed"));
        }

        return DefaultTaskResult.success();
    }
}
