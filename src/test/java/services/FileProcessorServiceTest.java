package com.readLegacies.maglabReadLegacies.services;

import com.readLegacies.maglabReadLegacies.contribuinte.OrderModel;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorServiceTest {

    @Test
    void processFile() throws IOException {
        FileProcessorService service = new FileProcessorService();

        File file = new ClassPathResource("data_1.txt").getFile();
        String fileName = file.getName();

        List<OrderModel> result = service.processFile(fileName);

        assertFalse(result.isEmpty(), "A lista de pedidos não deveria estar vazia");
    }
}