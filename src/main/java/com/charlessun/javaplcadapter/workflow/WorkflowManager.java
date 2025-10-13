package com.charlessun.javaplcadapter.workflow;

public interface WorkflowManager {
    void registerGroup(WorkflowGroup group);
    void startAll();
    void shutdown();
}
