package com.charlessun.javaplcadapter.domain.common;

/**
 * 可附加資料的結果
 *
 * @param <T> 資料類型
 */
public interface DataResult<T> extends Result {
    T getData();
}
