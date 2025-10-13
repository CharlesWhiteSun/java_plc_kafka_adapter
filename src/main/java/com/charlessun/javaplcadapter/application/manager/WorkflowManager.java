package com.charlessun.javaplcadapter.application.manager;

import com.charlessun.javaplcadapter.application.executor.WorkflowExecutor;
import com.charlessun.javaplcadapter.workflow.Workflow;
import com.charlessun.javaplcadapter.workflow.WorkflowGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * WorkflowManager 負責：
 * - 管理多組 WorkflowGroup
 * - 利用 WorkflowExecutor 控制執行、排程與併行
 */
public class WorkflowManager {

    private final Map<String, WorkflowGroup> groups = new ConcurrentHashMap<>();
    private final WorkflowExecutor executor;

    public WorkflowManager(int poolSize) {
        this.executor = new WorkflowExecutor(poolSize);
    }

    public void registerGroup(String name, WorkflowGroup group) {
        groups.put(name, group);
    }

    /** 啟動所有群組（一次性執行） */
    public void startAllOnce() {
        groups.values().forEach(group ->
                group.getWorkflows().forEach(workflow ->
                        executor.submit(() -> {
                            try {
                                System.out.println("[WorkflowManager] Executing once: " + workflow.getName());
                                workflow.execute();
                            } catch (Exception e) {
                                System.err.println("[WorkflowManager] Workflow failed: " + workflow.getName() + " - " + e.getMessage());
                                e.printStackTrace();
                            }
                        })
                )
        );
    }

    /** 啟動所有群組（固定週期執行） */
    public void startAllPeriodically(long period, TimeUnit unit) {
        groups.values().forEach(group ->
                group.getWorkflows().forEach(workflow ->
                        executor.scheduleAtFixedRate(() -> {
                            try {
                                System.out.println("[WorkflowManager] Periodic execute: " + workflow.getName());
                                workflow.execute();
                            } catch (Exception e) {
                                System.err.println("[WorkflowManager] Periodic workflow failed: " + workflow.getName() + " - " + e.getMessage());
                                e.printStackTrace();
                            }
                        }, 0, period, unit)
                )
        );
    }

    /** 啟動特定群組（一次性執行） */
    public void startGroupOnce(String name) {
        WorkflowGroup group = groups.get(name);
        if (group != null) {
            group.getWorkflows().forEach(workflow ->
                    executor.submit(() -> {
                        try {
                            System.out.println("[WorkflowManager] Executing group: " + name);
                            workflow.execute();
                        } catch (Exception e) {
                            System.err.println("[WorkflowManager] Workflow in group " + name + " failed: " + workflow.getName() + " - " + e.getMessage());
                            e.printStackTrace();
                        }
                    })
            );
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
