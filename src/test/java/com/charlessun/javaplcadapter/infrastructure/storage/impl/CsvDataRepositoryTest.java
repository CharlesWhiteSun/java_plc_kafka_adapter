package com.charlessun.javaplcadapter.infrastructure.storage.impl;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("unchecked")
public class CsvDataRepositoryTest {

    private Path tempFile;
    private CsvDataRepository.CsvMapper<TestEntity> mapper;
    private CsvDataRepository<TestEntity> repository;

    @BeforeEach
    void setup() throws IOException {
        tempFile = Files.createTempFile("csv_test_", ".csv");
        Files.write(tempFile, new byte[0]); // 確保檔案是空的

        // Mockito 模擬 CsvMapper
        mapper = mock(CsvDataRepository.CsvMapper.class);
        when(mapper.header()).thenReturn("name,value");
        when(mapper.toCsvRow(any(TestEntity.class)))
                .thenAnswer(invocation -> {
                    TestEntity e = invocation.getArgument(0);
                    return e.getName() + "," + e.getValue();
                });

        repository = new CsvDataRepository<>(tempFile.toString(), mapper);
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testSave_Success() throws IOException {
        TestEntity entity = new TestEntity("test", 123);

        var result = repository.save(entity);

        assertTrue(result.success());
        assertEquals("CSV 寫入成功: " + entity, result.message());

        verify(mapper, times(1)).toCsvRow(argThat(e ->
                e.getName().equals("test") && e.getValue() == 123
        ));

        var lines = Files.readAllLines(tempFile);
        assertEquals(mapper.header(), lines.get(0));
        assertEquals("test,123", lines.get(1));
    }

    @Test
    void testCsvFileContent() throws IOException {
        TestEntity entity1 = new TestEntity("apple", 10);
        TestEntity entity2 = new TestEntity("banana", 20);

        repository.save(entity1);
        repository.save(entity2);

        verify(mapper, times(2)).toCsvRow(any(TestEntity.class));

        var lines = Files.readAllLines(tempFile);
        assertEquals("name,value", lines.get(0)); // header
        assertEquals("apple,10", lines.get(1));
        assertEquals("banana,20", lines.get(2));
    }

    @Test
    void testSave_MapperThrowsException() {
        when(mapper.toCsvRow(any())).thenThrow(new RuntimeException("轉換失敗"));

        var result = repository.save(new TestEntity("error", 999));

        assertFalse(result.success());
        assertNotNull(result.error());
        assertEquals("轉換失敗", result.error().getMessage());
    }


    static class TestEntity {
        private final String name;
        private final int value;

        public TestEntity(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "TestEntity{name='" + name + "', value=" + value + "}";
        }
    }
}
