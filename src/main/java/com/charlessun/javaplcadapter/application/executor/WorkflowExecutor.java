package com.charlessun.javaplcadapter.application.executor;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class WorkflowExecutor {

    // 固定執行緒池（適用多工作流並行）
    private final ThreadPoolExecutor executor;

    // 週期任務排程器（適用於固定時間重複執行）
    private final ScheduledExecutorService scheduler;

    public WorkflowExecutor(int poolSize) {
        this.executor = new ThreadPoolExecutor(
                poolSize,                      // core pool size
                poolSize,                      // maximum pool size
                60L, TimeUnit.SECONDS,         // keep-alive
                new LinkedBlockingQueue<>(),   // task queue
                new NamedThreadFactory("WorkflowExecutor"),
                new ThreadPoolExecutor.CallerRunsPolicy() // 飽和策略
        );
        this.scheduler = Executors.newScheduledThreadPool(2, new NamedThreadFactory("WorkflowScheduler"));
    }

    /** 提交一次性任務 */
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    /** 提交回傳結果的任務 */
    public <T> Future<T> submit(Supplier<T> task) {
        return executor.submit(() -> task.get());
    }

    /** 延遲執行任務 */
    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return scheduler.schedule(task, delay, unit);
    }

    /** 週期執行任務（固定頻率） */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /** 優雅關閉執行緒池 */
    public void shutdown() {
        try {
            executor.shutdown();
            scheduler.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
