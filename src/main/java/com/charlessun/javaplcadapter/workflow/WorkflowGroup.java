package com.charlessun.javaplcadapter.workflow;

import java.util.List;

public class WorkflowGroup {
    private final String name;
    private final List<Workflow> workflows;

    public WorkflowGroup(String name, List<Workflow> workflows) {
        this.name = name;
        this.workflows = workflows;
    }

    public String getName() {
        return name;
    }

    public List<Workflow> getWorkflows() {
        return workflows;
    }
}
