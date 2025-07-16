package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();

        // Produkt-IDs durchgehen und Optional prüfen
        for (String productId : productIds) {

            // Rückgabe ist jetzt Optional
            Optional<Product> productOpt = productRepo.getProductById(productId);
            if (productOpt.isEmpty()) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            // Produkt aus dem Optional extrahieren
            products.add(productOpt.get());
        }

        // Order hat jetzt zusätzlich den Bestellstatus als dritten Parameter
        Order newOrder = new Order(
                // eindeutige ID erzeugen
                UUID.randomUUID().toString(),
                products,

                // Standardstatus beim Erstellen einer neuen Bestellung
                OrderStatus.PROCESSING
        );
        return orderRepo.addOrder(newOrder);
    }
}
