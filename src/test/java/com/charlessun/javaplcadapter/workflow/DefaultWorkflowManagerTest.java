package com.charlessun.javaplcadapter.workflow;

import com.charlessun.javaplcadapter.workflow.impl.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultWorkflowManagerTest {

    private static DefaultWorkflowManager manager;

    @BeforeAll
    static void setup() {
        manager = new DefaultWorkflowManager(3);

        // === Group A (ONCE) ===
        DefaultWorkflowGroup groupA = new DefaultWorkflowGroup("GroupA");
        DefaultWorkflow workflowA1 = new DefaultWorkflow("Workflow-A1", WorkflowPolicy.ONCE);
        workflowA1.addTask(new ExampleTask("FetchData"));
        workflowA1.addTask(new ExampleTask("ProcessData"));
        workflowA1.addTask(new ExampleTask("SaveData"));
        groupA.addWorkflow(workflowA1);

        DefaultWorkflow workflowA2 = new DefaultWorkflow("Workflow-A2", WorkflowPolicy.ONCE);
        workflowA2.addTask(new ExampleTask("FetchData"));
        workflowA2.addTask(new ExampleTask("ProcessData"));
        workflowA2.addTask(new ExampleTask("SaveData"));
        groupA.addWorkflow(workflowA2);

        // === Group B (ONCE) ===
        DefaultWorkflowGroup groupB = new DefaultWorkflowGroup("GroupB");
        DefaultWorkflow workflowB1 = new DefaultWorkflow("Workflow-B1", WorkflowPolicy.ONCE);
        workflowB1.addTask(new ExampleTask("FetchData"));
        workflowB1.addTask(new ExampleTask("ProcessData"));
        workflowB1.addTask(new ExampleTask("SaveData"));
        groupB.addWorkflow(workflowB1);

        // === Group C (PERIODIC) ===
        DefaultWorkflowGroup groupC = new DefaultWorkflowGroup("GroupC");
        DefaultWorkflow workflowC1 = new DefaultWorkflow("Workflow-C1", WorkflowPolicy.PERIODIC);
        workflowC1.addTask(new ExampleTask("Task1"));
        workflowC1.addTask(new ExampleTask("Task2"));
        groupC.addWorkflow(workflowC1);

        // === Group D (INFINITE) ===
        DefaultWorkflowGroup groupD = new DefaultWorkflowGroup("GroupD");
        DefaultWorkflow workflowD1 = new DefaultWorkflow("Workflow-D1", WorkflowPolicy.INFINITE);
        workflowD1.addTask(new ExampleTask("TaskA"));
        workflowD1.addTask(new ExampleTask("TaskB"));
        groupD.addWorkflow(workflowD1);

        // 註冊群組
        manager.registerGroup(groupA);
        manager.registerGroup(groupB);
        manager.registerGroup(groupC);
        manager.registerGroup(groupD);
    }

    @AfterAll
    static void tearDown() {
        manager.shutdown();
    }

    @Test
    void testONCEWorkflows() throws InterruptedException {
        System.out.println("=== Test ONCE Workflows ===");

        manager.getGroup("GroupA").getWorkflows().forEach(workflow -> {
            if (workflow.getPolicy() == WorkflowPolicy.ONCE) {
                new Thread(() -> {
                    try {
                        workflow.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        manager.getGroup("GroupB").getWorkflows().forEach(workflow -> {
            if (workflow.getPolicy() == WorkflowPolicy.ONCE) {
                new Thread(() -> {
                    try {
                        workflow.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        assertEquals(2, manager.getGroup("GroupA").getWorkflows().size());
        assertEquals(1, manager.getGroup("GroupB").getWorkflows().size());
    }

    @Test
    void testPERIODICWorkflow() throws InterruptedException {
        System.out.println("=== Test PERIODIC Workflow (run 3 times) ===");

        manager.getGroup("GroupC").getWorkflows().forEach(workflow -> {
            if (workflow.getPolicy() == WorkflowPolicy.PERIODIC) {
                new Thread(() -> {
                    try {
                        for (int i = 0; i < 3; i++) {
                            workflow.execute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
        assertEquals(1, manager.getGroup("GroupC").getWorkflows().size());
    }

    @Test
    void testINFINITEWorkflow() throws InterruptedException {
        System.out.println("=== Test INFINITE Workflow (run 3 times) ===");

        manager.getGroup("GroupD").getWorkflows().forEach(workflow -> {
            if (workflow.getPolicy() == WorkflowPolicy.INFINITE) {
                new Thread(() -> {
                    try {
                        for (int i = 0; i < 3; i++) {
                            workflow.execute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
        assertEquals(1, manager.getGroup("GroupD").getWorkflows().size());
    }
}
