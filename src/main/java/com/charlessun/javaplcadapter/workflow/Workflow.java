package com.charlessun.javaplcadapter.workflow;

public class Workflow {

    private final String name;

    public Workflow(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void execute() {
        System.out.println("[Workflow] Executing workflow: " + name + " on thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(500); // 模擬執行時間
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
