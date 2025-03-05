package com.readLegacies.maglabReadLegacies.services;

import com.readLegacies.maglabReadLegacies.contribuinte.OrderModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileProcessorService {

    public List<OrderModel> processFile(String fileName) throws IOException {
        File file = getFileFromResources(fileName);
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado: " + fileName);
        }

        Map<Long, OrderModel> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line, users);
            }
        }
        return new ArrayList<>(users.values());
    }

    private File getFileFromResources(String fileName) throws IOException {
        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }

        ClassPathResource resource = new ClassPathResource(fileName);
        return resource.getFile();
    }

    private void parseLine(String line, Map<Long, OrderModel> users) {
        Long userId = Long.parseLong(line.substring(0, 10).trim());
        String name = line.substring(10, 55).trim();
        Long orderId = Long.parseLong(line.substring(55, 65).trim());
        Long productId = Long.parseLong(line.substring(65, 75).trim());
        BigDecimal value = new BigDecimal(line.substring(75, 87).trim());
        LocalDate date = LocalDate.parse(line.substring(87, 95), DateTimeFormatter.ofPattern("yyyyMMdd"));

        users.putIfAbsent(userId, new OrderModel(userId, name));
        OrderModel user = users.get(userId);

        Optional<OrderModel.Order> existingOrder = user.getOrders().stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();

        if (existingOrder.isPresent()) {
            existingOrder.get().getProducts().add(new OrderModel.Product(productId, value));
            existingOrder.get().setTotal(existingOrder.get().getTotal().add(value));
        } else {
            List<OrderModel.Product> products = new ArrayList<>();
            products.add(new OrderModel.Product(productId, value));
            user.getOrders().add(new OrderModel.Order(orderId, value, date.toString(), products));
        }
    }

    public List<OrderModel> filterOrders(String fileName, Long orderId, LocalDate startDate, LocalDate endDate) throws IOException {
        return processFile(fileName).stream()
                .map(user -> {
                    List<OrderModel.Order> filteredOrders = user.getOrders().stream()
                            .filter(order -> (orderId == null || order.getOrderId().equals(orderId)) &&
                                    (startDate == null || endDate == null ||
                                            (order.getDate().compareTo(startDate.toString()) >= 0 &&
                                                    order.getDate().compareTo(endDate.toString()) <= 0)))
                            .collect(Collectors.toList());

                    return new OrderModel(user.getUserId(), user.getName());
                })
                .filter(user -> !user.getOrders().isEmpty())
                .collect(Collectors.toList());
    }
}
