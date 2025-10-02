package com.charlessun.javaplcadapter.infrastructure.db.impl;

import com.charlessun.javaplcadapter.domain.common.impl.SaveResult;
import com.charlessun.javaplcadapter.infrastructure.db.DataRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * CSV 實作的 DataRepository
 *
 * @param <T> 實體資料型別
 */
public class CsvDataRepository<T> implements DataRepository<T, SaveResult> {

    private final String filePath;
    private final CsvMapper<T> mapper;

    public CsvDataRepository(String filePath, CsvMapper<T> mapper) {
        this.filePath = filePath;
        this.mapper = mapper;

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path) || Files.size(path) == 0) {
                try (FileWriter writer = new FileWriter(filePath, true)) {
                    writer.append(mapper.header()).append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("建立 CSV 檔案失敗: " + e.getMessage());
        }
    }

    @Override
    public SaveResult save(T entity) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(mapper.toCsvRow(entity)).append("\n");
            return SaveResult.success("CSV 寫入成功: " + entity);
        } catch (IOException e) {
            return SaveResult.failure(e);
        }
    }

    /**
     * 將實體物件轉換為 CSV 行
     */
    public interface CsvMapper<T> {
        String header(); // CSV 標頭
        String toCsvRow(T entity); // 將物件轉成 CSV 行
    }
}
