package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        productRepo = new ProductRepo();
        orderRepo = new OrderListRepo();  // Oder OrderMapRepo, je nachdem
        shopService = new ShopService(productRepo, orderRepo);
    }

    @Test
    void addOrder_withValidProductIds_createsOrder() {
        List<String> productIds = List.of("1");

        Order order = shopService.addOrder(productIds);

        assertNotNull(order.id());
        assertEquals(1, order.products().size());
        assertEquals("1", order.products().get(0).id());
        assertEquals(OrderStatus.PROCESSING, order.status());
        assertNotNull(order.orderDate());
    }

    @Test
    void addOrder_withInvalidProductId_throwsException() {
        List<String> productIds = List.of("999"); // ungültige ID

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class, () -> {
            shopService.addOrder(productIds);
        });

        assertEquals("999", ex.getProductId());
    }

    @Test
    void updateOrder_updatesStatusSuccessfully() {
        // Vorbereitung: Bestellung anlegen und hinzufügen
        Order order = new Order(UUID.randomUUID().toString(),
                List.of(new Product("1", "Apfel")),
                OrderStatus.PROCESSING,
                Instant.now());
        orderRepo.addOrder(order);

        Order updated = shopService.updateOrder(order.id(), OrderStatus.COMPLETED);

        assertEquals(OrderStatus.COMPLETED, updated.status());
    }

    @Test
    void updateOrder_withInvalidOrderId_throwsException() {
        assertThrows(OrderNotFoundException.class, () -> {
            shopService.updateOrder("invalid-id", OrderStatus.COMPLETED);
        });
    }

    @Test
    void getOrdersByStatus_returnsCorrectOrders() {
        // Vorbereitung: Mehrere Bestellungen mit unterschiedlichen Status
        Order order1 = new Order(UUID.randomUUID().toString(),
                List.of(new Product("1", "Apfel")),
                OrderStatus.PROCESSING,
                Instant.now());

        Order order2 = new Order(UUID.randomUUID().toString(),
                List.of(new Product("1", "Apfel")),
                OrderStatus.COMPLETED,
                Instant.now());

        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);

        List<Order> processingOrders = shopService.getOrdersByStatus(OrderStatus.PROCESSING);
        assertEquals(1, processingOrders.size());
        assertEquals(OrderStatus.PROCESSING, processingOrders.get(0).status());

        List<Order> completedOrders = shopService.getOrdersByStatus(OrderStatus.COMPLETED);
        assertEquals(1, completedOrders.size());
        assertEquals(OrderStatus.COMPLETED, completedOrders.get(0).status());
    }
}
