package org.example;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Order mit der ID '" + orderId + "' wurde nicht gefunden.");
    }
}
