package org.example;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("Produkt mit der ID '" + productId + "' wurde nicht gefunden.");
    }
}
