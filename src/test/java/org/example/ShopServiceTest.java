package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    // Testet, ob eine Bestellung mit gültigen Produkt-IDs erfolgreich erstellt wird
    @Test
    void addOrderTest_validProductIds_createsOrder() {
        // GIVEN
        ShopService shopService = new ShopService();
        List<String> productIds = List.of("1");

        // WHEN
        Order actual = shopService.addOrder(productIds);

        // THEN
        assertNotNull(actual.id(), "Bestell-ID sollte nicht null sein");
        assertEquals(1, actual.products().size(), "Es sollte genau 1 Produkt in der Bestellung sein");
        assertEquals("1", actual.products().get(0).id(), "Produkt-ID sollte '1' sein");
        assertEquals(OrderStatus.PROCESSING, actual.status(), "Bestellstatus sollte PROCESSING sein");
    }

    // Testet, ob bei ungültiger Produkt-ID eine ProductNotFoundException geworfen wird
    @Test
    void addOrderTest_invalidProductId_throwsException() {
        // GIVEN
        ShopService shopService = new ShopService();
        List<String> productIds = List.of("1", "2");

        // WHEN & THEN
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            shopService.addOrder(productIds);
        });

        assertEquals("2", exception.getProductId(), "Exception sollte die ungültige Produkt-ID enthalten");
    }
}
