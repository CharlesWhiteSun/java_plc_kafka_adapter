package com.charlessun.javaplcadapter.application.manager;

import com.charlessun.javaplcadapter.workflow.Workflow;
import com.charlessun.javaplcadapter.workflow.WorkflowGroup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

class WorkflowManagerTest {

    private static WorkflowManager manager;

    @BeforeAll
    static void setup() {
        manager = new WorkflowManager(4);
        WorkflowGroup group1 = new WorkflowGroup("GroupA",
                List.of(new Workflow("A1"), new Workflow("A2")));
        WorkflowGroup group2 = new WorkflowGroup("GroupB",
                List.of(new Workflow("B1")));

        manager.registerGroup("GroupA", group1);
        manager.registerGroup("GroupB", group2);
    }

    @AfterAll
    static void tearDown() {
        manager.shutdown();
    }

    @Test
    void testStartAllOnce() throws InterruptedException {
        System.out.println("=== Start All Once ===");
        manager.startAllOnce();
        Thread.sleep(2000);
    }

    @Test
    void testStartAllPeriodically() throws InterruptedException {
        System.out.println("=== Start All Periodically (every 2 seconds) ===");
        manager.startAllPeriodically(2, TimeUnit.SECONDS);
        Thread.sleep(7000); // 等待幾次週期執行
    }

    @Test
    void testStartSingleGroup() throws InterruptedException {
        System.out.println("=== Start GroupA Once ===");
        manager.startGroupOnce("GroupA");
        Thread.sleep(2000);
    }
}
