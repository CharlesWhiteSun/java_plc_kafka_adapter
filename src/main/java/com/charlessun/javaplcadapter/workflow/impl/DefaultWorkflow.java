package com.charlessun.javaplcadapter.workflow.impl;

import com.charlessun.javaplcadapter.workflow.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultWorkflow implements Workflow {
    private final String name;
    private final WorkflowPolicy policy;
    private final List<Task> tasks = new ArrayList<>();
    private final WorkflowContext context = new WorkflowContext();

    public DefaultWorkflow(String name, WorkflowPolicy policy) {
        this.name = name;
        this.policy = policy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public WorkflowPolicy getPolicy() {
        return policy;
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void execute() {
        do {
            System.out.println("🔹 Executing workflow: " + name);
            for (Task task : tasks) {
                try {
                    TaskResult result = task.execute(context);
                    if (!result.isSuccess()) {
                        System.err.println("❌ Task failed: " + task.getName() +
                                " - " + result.getError().getMessage());
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("💥 Exception in task " + task.getName() + ": " + e.getMessage());
                    break;
                }
            }

            if (policy == WorkflowPolicy.ONCE) break;

        } while (policy == WorkflowPolicy.INFINITE || policy == WorkflowPolicy.PERIODIC);
    }
}
