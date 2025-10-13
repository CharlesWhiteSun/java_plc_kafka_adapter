package com.charlessun.javaplcadapter.workflow.impl;

import com.charlessun.javaplcadapter.workflow.*;

import java.util.*;
import java.util.concurrent.*;

public class DefaultWorkflowManager implements WorkflowManager {
    private final Map<String, WorkflowGroup> groupMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService;

    public DefaultWorkflowManager(int poolSize) {
        this.executorService = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void registerGroup(WorkflowGroup group) {
        groupMap.put(group.getGroupName(), group);
    }

    @Override
    public void startAll() {
        System.out.println("🚀 Starting all workflow groups...");
        for (WorkflowGroup group : groupMap.values()) {
            for (Workflow workflow : group.getWorkflows()) {
                executorService.submit(new WorkflowExecutor(workflow));
            }
        }
    }

    @Override
    public void shutdown() {
        System.out.println("🛑 Shutting down workflow manager...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public WorkflowGroup getGroup(String groupName) {
        return this.groupMap.get(groupName);
    }
}
