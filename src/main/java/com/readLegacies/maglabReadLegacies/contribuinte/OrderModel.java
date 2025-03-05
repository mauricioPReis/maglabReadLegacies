package com.readLegacies.maglabReadLegacies.contribuinte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderModel {
    private Long userId;
    private String name;
    private List<Order> orders = new ArrayList<>();

    public OrderModel(Long userId, String name, List<Order> orders) {
        this.userId = userId;
        this.name = name;
        this.orders = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    public static class Order {
        private Long orderId;
        private BigDecimal total;
        private String date;
        private List<Product> products = new ArrayList<>();

        public Order(Long orderId, BigDecimal total, String date) {
            this.orderId = orderId;
            this.total = total;
            this.date = date;
            this.products = new ArrayList<>();
        }
    }

    @Data
    @NoArgsConstructor
    public static class Product {
        private Long productId;
        private BigDecimal value;

        public Product(Long productId, BigDecimal value) {
            this.productId = productId;
            this.value = value;
        }
    }
}
