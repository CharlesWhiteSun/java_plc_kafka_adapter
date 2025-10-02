package com.charlessun.javaplcadapter.domain.common.impl;

import com.charlessun.javaplcadapter.domain.common.ErrorResult;
import com.charlessun.javaplcadapter.domain.common.MessageResult;
import com.charlessun.javaplcadapter.domain.common.Result;
import org.jetbrains.annotations.NotNull;

/**
 * 預設的 SaveResult 實作，繼承 Result、MessageResult、ErrorResult
 */
public record SaveResult(boolean success, String message,
                         Exception error) implements Result, MessageResult, ErrorResult {

    public static SaveResult success(String message) {
        return new SaveResult(true, message, null);
    }

    public static SaveResult failure(Exception error) {
        return new SaveResult(false, null, error);
    }

    @Override
    @NotNull
    public String toString() {
        if (success) {
            return "✅ Success: " + message;
        } else {
            return "❌ Failure: " + (error != null ? error.getMessage() : "Unknown error");
        }
    }
}
