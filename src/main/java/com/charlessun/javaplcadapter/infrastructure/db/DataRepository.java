package com.charlessun.javaplcadapter.infrastructure.db;

import com.charlessun.javaplcadapter.domain.common.impl.SaveResult;

/**
 * 通用資料庫儲存介面
 *
 * @param <T> 實體型別
 * @param <R> 儲存結果型別
 */
public interface DataRepository<T, R extends SaveResult> {
    R save(T entity);
}
