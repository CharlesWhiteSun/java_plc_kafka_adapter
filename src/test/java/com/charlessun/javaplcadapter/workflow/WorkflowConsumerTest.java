package com.charlessun.javaplcadapter.workflow;

import com.charlessun.javaplcadapter.adapter.consumer.Consumer;
import com.charlessun.javaplcadapter.adapter.consumer.impl.GenericConsumer;
import com.charlessun.javaplcadapter.workflow.impl.ConsumerTask;
import com.charlessun.javaplcadapter.workflow.impl.DefaultWorkflow;
import com.charlessun.javaplcadapter.workflow.impl.DefaultWorkflowGroup;
import com.charlessun.javaplcadapter.workflow.impl.DefaultWorkflowManager;
import com.charlessun.javaplcadapter.workflow.impl.ExampleTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WorkflowConsumerTest {

    private static DefaultWorkflowManager manager;

    @BeforeAll
    static void setup() {
        // 建立 Workflow Manager
        manager = new DefaultWorkflowManager(4);

        // === Group 消費者 A ===
        DefaultWorkflowGroup groupA = new DefaultWorkflowGroup("ConsumerGroupA");

        Consumer consumerA = new GenericConsumer("Consumer-A");
        ConsumerRecord<String, String> recordA = new ConsumerRecord<>("Topic-A", 0, 0L, "key1", "message1");

        DefaultWorkflow workflowA1 = new DefaultWorkflow("FetchWorkflow-A1", WorkflowPolicy.ONCE);
        workflowA1.addTask(new ConsumerTask("[A1] 消費 Kafka 訊息", consumerA, recordA));
        workflowA1.addTask(new ExampleTask("[A1] 處理資料"));
        workflowA1.addTask(new ExampleTask("[A1] 儲存資料"));
        groupA.addWorkflow(workflowA1);

        // === Group 消費者 B ===
        DefaultWorkflowGroup groupB = new DefaultWorkflowGroup("ConsumerGroupB");

        Consumer consumerB = new GenericConsumer("Consumer-B");
        ConsumerRecord<String, String> recordB = new ConsumerRecord<>("Topic-B", 0, 0L, "key2", "message2");

        DefaultWorkflow workflowB1 = new DefaultWorkflow("FetchWorkflow-B1", WorkflowPolicy.PERIODIC);
        workflowB1.setPeriodicConfig(3, 200); // 執行 3 次，每次 200ms
        workflowB1.addTask(new ConsumerTask("[B1] 消費 Kafka 訊息", consumerB, recordB));
        workflowB1.addTask(new ExampleTask("[B1] 處理資料"));
        workflowB1.addTask(new ExampleTask("[B1] 儲存資料"));
        groupB.addWorkflow(workflowB1);

        // 註冊群組
        manager.registerGroup(groupA);
        manager.registerGroup(groupB);
    }

    @AfterAll
    static void tearDown() {
        if (manager != null) {
            manager.shutdown();
        }
    }

    @Test
    void testConsumerWorkflows() throws InterruptedException {
        manager.startAll();
        // 因為 B 群組是 PERIODIC 3 次，每次 200ms，所以等待 1 秒足夠
        Thread.sleep(1500);

        assert manager.getGroup("ConsumerGroupA").getWorkflows().size() == 1;
        assert manager.getGroup("ConsumerGroupB").getWorkflows().size() == 1;
    }
}
