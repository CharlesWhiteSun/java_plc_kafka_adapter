package com.charlessun.javaplcadapter.workflow;

public interface Workflow {
    String getName();
    WorkflowPolicy getPolicy();
    void addTask(Task task);
    void execute() throws Exception;
}
