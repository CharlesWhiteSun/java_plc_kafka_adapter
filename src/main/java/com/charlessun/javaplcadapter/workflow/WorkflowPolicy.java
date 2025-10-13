package com.charlessun.javaplcadapter.workflow;

public enum WorkflowPolicy {
    ONCE,        // 執行一次即結束
    PERIODIC,    // 固定週期重複執行
    INFINITE     // 無限循環直到手動停止
}
