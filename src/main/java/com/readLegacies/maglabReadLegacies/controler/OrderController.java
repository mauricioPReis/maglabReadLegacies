package com.readLegacies.maglabReadLegacies.controler;

import com.readLegacies.maglabReadLegacies.services.FileProcessorService;
import com.readLegacies.maglabReadLegacies.contribuinte.OrderModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Pedidos", description = "API para processar e visualizar pedidos")
public class OrderController {

    private final FileProcessorService fileProcessorService;

    public OrderController(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os pedidos", description = "Processa o arquivo e retorna os pedidos normalizados")
    public List<OrderModel> getOrders(
            @Parameter(description = "Nome do arquivo (exemplo: 'data_1')")
            @RequestParam String fileName) throws IOException {
        return fileProcessorService.processFile(fileName);
    }

    @GetMapping("/filter")
    @Operation(summary = "Filtra pedidos", description = "Filtra pedidos por ID e intervalo de datas")
    public List<OrderModel> filterOrders(
            @RequestParam String fileName,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws IOException {

        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;

        return fileProcessorService.filterOrders(fileName, orderId, start, end);
    }
}
