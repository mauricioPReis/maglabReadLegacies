package com.readLegacies.maglabReadLegacies.controler;
import org.springframework.web.bind.annotation.*;
import com.readLegacies.maglabReadLegacies.contribuinte.OrderModel;
import com.readLegacies.maglabReadLegacies.services.FileProcessorService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final FileProcessorService fileProcessorService;

    public OrderController(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }

    @GetMapping
    public List<OrderModel> getOrders(@RequestParam String filePath) throws IOException {
        return fileProcessorService.processFile(filePath);
    }

    @GetMapping("/filter")
    public List<OrderModel> filterOrders(
            @RequestParam String filePath,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws IOException {

        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;

        return fileProcessorService.filterOrders(filePath, orderId, start, end);
    }
}
