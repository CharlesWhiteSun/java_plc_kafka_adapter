package com.charlessun.javaplcadapter.workflow;

import java.util.concurrent.Callable;

public class WorkflowExecutor implements Callable<Void> {
    private final Workflow workflow;

    public WorkflowExecutor(Workflow workflow) {
        this.workflow = workflow;
    }

    @Override
    public Void call() {
        try {
            workflow.execute();
        } catch (Exception e) {
            System.err.println("💥 WorkflowExecutor failed: " + workflow.getName() + " - " + e.getMessage());
        }
        return null;
    }
}
