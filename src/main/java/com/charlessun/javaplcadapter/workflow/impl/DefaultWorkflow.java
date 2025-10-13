package com.charlessun.javaplcadapter.workflow.impl;

import com.charlessun.javaplcadapter.workflow.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DefaultWorkflow implements Workflow {
    private final String name;
    private final WorkflowPolicy policy;
    private final List<Task> tasks = new ArrayList<>();
    private final WorkflowContext context = new WorkflowContext();

    private int periodicCounts = -1;        // -1 表示無限次
    private long periodicIntervalMs = 1000; // 每次間隔毫秒

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> scheduledFuture;

    public DefaultWorkflow(String name, WorkflowPolicy policy) {
        this.name = name;
        this.policy = policy;
    }

    public void setPeriodicConfig(int counts, long intervalMs) {
        this.periodicCounts = counts;
        this.periodicIntervalMs = intervalMs;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public WorkflowPolicy getPolicy() {
        return this.policy;
    }

    @Override
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    @Override
    public void execute() {
        switch (this.policy) {
            case ONCE -> {
                runTasksOnce();
            }
            case PERIODIC -> {
                int count = 0;
                while (this.periodicCounts < 0 || count < this.periodicCounts) {
                    runTasksOnce();
                    count++;
                    try {
                        Thread.sleep(this.periodicIntervalMs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            case INFINITE -> {
                scheduledFuture = scheduler.scheduleWithFixedDelay(this::runTasksOnce, 0, 0, TimeUnit.MILLISECONDS);
            }
            default -> throw new IllegalStateException("Unknown policy: " + this.policy);
        }
    }

    // 封裝執行任務的共用方法
    private void runTasksOnce() {
        System.out.println("🔹 Executing workflow once: " + this.name);
        for (Task task : this.tasks) {
            try {
                TaskResult result = task.execute(this.context);
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
    }

    // 提供取消排程
    public void stop() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        scheduler.shutdownNow();
    }
}
