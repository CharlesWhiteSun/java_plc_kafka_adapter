package com.charlessun.javaplcadapter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    /**
     * 取得指定類別的 Logger
     * @param clazz 要取得 Logger 的類別
     * @return Logger 實例
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
