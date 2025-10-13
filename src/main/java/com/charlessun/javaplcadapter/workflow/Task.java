package com.charlessun.javaplcadapter.workflow;

public interface Task {
    String getName();
    TaskResult execute(WorkflowContext context) throws Exception;
}
