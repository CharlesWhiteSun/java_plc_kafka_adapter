package com.charlessun.javaplcadapter.domain.common;

/**
 * 可附加錯誤例外的結果
 */
public interface ErrorResult extends Result {
    Exception error();
}
