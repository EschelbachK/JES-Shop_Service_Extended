package org.example;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepo {
    private List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Order addOrder(Order newOrder) {
        orders.add(newOrder);
        return newOrder;
    }

    @Override
    public void removeOrder(String id) {
        orders.removeIf(order -> order.id().equals(id));
    }

    // Bestellung aktualisieren
    @Override
    public Order updateOrder(Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).id().equals(updatedOrder.id())) {
                orders.set(i, updatedOrder);
                return updatedOrder;
            }
        }
        return null;
    }
}
