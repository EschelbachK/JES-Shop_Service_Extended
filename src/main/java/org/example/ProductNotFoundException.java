package org.example;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {
    private final String productId;

    public ProductNotFoundException(String productId) {
        super("Produkt mit ID " + productId + " nicht gefunden");
        this.productId = productId;
    }

}
