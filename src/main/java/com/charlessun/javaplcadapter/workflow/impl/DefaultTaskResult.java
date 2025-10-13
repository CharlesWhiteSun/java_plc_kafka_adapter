package com.charlessun.javaplcadapter.workflow.impl;

import com.charlessun.javaplcadapter.workflow.TaskResult;

public class DefaultTaskResult implements TaskResult {
    private final boolean success;
    private final Exception error;

    public DefaultTaskResult(boolean success, Exception error) {
        this.success = success;
        this.error = error;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public Exception getError() {
        return error;
    }

    public static DefaultTaskResult success() {
        return new DefaultTaskResult(true, null);
    }

    public static DefaultTaskResult failure(Exception e) {
        return new DefaultTaskResult(false, e);
    }
}
