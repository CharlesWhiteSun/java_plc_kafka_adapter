package com.charlessun.javaplcadapter.workflow;

import java.util.List;

public interface WorkflowGroup {
    String getGroupName();
    List<Workflow> getWorkflows();
    void addWorkflow(Workflow workflow);
}
