package com.charlessun.javaplcadapter.workflow.impl;

import com.charlessun.javaplcadapter.workflow.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultWorkflowGroup implements WorkflowGroup {
    private final String groupName;
    private final List<Workflow> workflows = new ArrayList<>();

    public DefaultWorkflowGroup(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public List<Workflow> getWorkflows() {
        return workflows;
    }

    @Override
    public void addWorkflow(Workflow workflow) {
        workflows.add(workflow);
    }
}
