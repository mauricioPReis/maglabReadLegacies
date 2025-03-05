package services;

import com.readLegacies.maglabReadLegacies.contribuinte.OrderModel;
import com.readLegacies.maglabReadLegacies.services.FileProcessorService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class FileProcessorServiceTest {

    @Test
    void processFile() throws IOException {
        FileProcessorService service = new FileProcessorService();
        List<OrderModel> result = service.processFile("src/test/resources/data_1.txt");
        assertFalse(result.isEmpty());
    }
}