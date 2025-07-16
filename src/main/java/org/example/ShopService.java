package org.example;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();

        for (String productId : productIds) {
            Optional<Product> productOpt = productRepo.getProductById(productId);
            if (productOpt.isEmpty()) {
                throw new ProductNotFoundException(productId);
            }
            products.add(productOpt.get());
        }

        Order newOrder = new Order(
                UUID.randomUUID().toString(),
                products,
                OrderStatus.PROCESSING,
                Instant.now()   // <-- Bestellzeitpunkt hinzufügen
        );

        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrder(String orderId, OrderStatus newStatus) {
        Optional<Order> existingOrderOpt = Optional.ofNullable(orderRepo.getOrderById(orderId));
        if (existingOrderOpt.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }

        Order updatedOrder = existingOrderOpt.get().withStatus(newStatus);
        return orderRepo.updateOrder(updatedOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getOrders()
                .stream()
                .filter(order -> order.status() == status)
                .toList();
    }


}
