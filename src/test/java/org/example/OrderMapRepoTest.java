package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    // Repository-Instanz für die Tests
    private OrderMapRepo repo;

    // Beispiel-Order, die für Tests verwendet wird
    private Order sampleOrder;

    // Vorbereitung vor jedem Test: Repository und Beispiel-Order anlegen
    @BeforeEach
    void setUp() {
        repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel");
        sampleOrder = new Order("1", List.of(product), OrderStatus.PROCESSING, Instant.now());
        repo.addOrder(sampleOrder);
    }

    // Test, ob getOrders() alle gespeicherten Bestellungen zurückgibt
    @Test
    void getOrders_returnsAllOrders() {
        List<Order> actual = repo.getOrders();

        assertEquals(1, actual.size());
        assertTrue(actual.contains(sampleOrder));
    }

    // Test, ob getOrderById() eine existierende Bestellung korrekt zurückgibt
    @Test
    void getOrderById_existingId_returnsOrder() {
        Order actual = repo.getOrderById("1");

        assertEquals(sampleOrder, actual);
    }

    // Test, ob getOrderById() bei nicht vorhandener ID null zurückgibt
    @Test
    void getOrderById_nonExistingId_returnsNull() {
        Order actual = repo.getOrderById("nonexistent");

        assertNull(actual);
    }

    // Test, ob addOrder() eine neue Bestellung korrekt hinzufügt und zurückgibt
    @Test
    void addOrder_addsOrderSuccessfully() {
        Product product = new Product("2", "Birne");
        Order newOrder = new Order("2", List.of(product), OrderStatus.PROCESSING, Instant.now());

        Order actual = repo.addOrder(newOrder);

        assertEquals(newOrder, actual);
        assertEquals(newOrder, repo.getOrderById("2"));
    }

    // Test, ob removeOrder() eine existierende Bestellung erfolgreich entfernt
    @Test
    void removeOrder_existingId_removesOrder() {
        repo.removeOrder("1");

        assertNull(repo.getOrderById("1"));
        assertTrue(repo.getOrders().isEmpty());
    }

    // Test, ob removeOrder() bei nicht vorhandener ID keine Änderungen vornimmt
    @Test
    void removeOrder_nonExistingId_noChange() {
        repo.removeOrder("nonexistent");

        assertEquals(1, repo.getOrders().size());
    }

    // Test, ob updateOrder() eine vorhandene Bestellung aktualisiert und zurückgibt
    @Test
    void updateOrder_existingOrder_updatesAndReturnsOrder() {
        Order updatedOrder = sampleOrder.withStatus(OrderStatus.IN_DELIVERY);

        Order result = repo.updateOrder(updatedOrder);

        assertEquals(updatedOrder, result);
        assertEquals(OrderStatus.IN_DELIVERY, repo.getOrderById("1").status());
    }

    // Test, ob updateOrder() bei nicht vorhandener Bestellung die Bestellung hinzufügt und zurückgibt
    @Test
    void updateOrder_nonExistingOrder_addsAndReturnsOrder() {
        Product product = new Product("3", "Orange");
        Order nonExisting = new Order("3", List.of(product), OrderStatus.PROCESSING, Instant.now());

        Order result = repo.updateOrder(nonExisting);

        assertEquals(nonExisting, result);
        assertEquals(nonExisting, repo.getOrderById("3"));
    }
}
