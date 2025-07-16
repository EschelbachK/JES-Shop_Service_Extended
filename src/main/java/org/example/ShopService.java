package org.example;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

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
                Instant.now()
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
        // Achtung: .toList() ist ab Java 16, für ältere Versionen bitte collect(Collectors.toList())
        return orderRepo.getOrders().stream()
                .filter(order -> order.status() == status)
                .toList();
    }
}
