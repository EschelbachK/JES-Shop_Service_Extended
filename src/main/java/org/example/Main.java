package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Repositories erstellen
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();

        // ShopService instanziieren
        ShopService shopService = new ShopService(productRepo, orderRepo);

        // Drei konkrete Bestellungen erstellen
        Order order1 = shopService.addOrder(List.of("1"));
        productRepo.addProduct(new Product("2", "Banane"));
        Order order2 = shopService.addOrder(List.of("2"));
        productRepo.addProduct(new Product("3", "Kirsche"));
        Order order3 = shopService.addOrder(List.of("3"));

        // Beispielhafte Ausgabe
        System.out.println("Bestellungen erfolgreich erstellt:");
        for (Order order : orderRepo.getOrders()) {
            System.out.println(order);
        }
    }
}
