package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    // Repository, das wir testen
    private OrderListRepo repo;

    // Beispielbestellung, die wir für Tests nutzen
    private Order sampleOrder;

    // Wird vor jedem Test ausgeführt, um das Repository und eine Beispielbestellung zu initialisieren
    @BeforeEach
    void setUp() {
        // Neues Repository erstellen
        repo = new OrderListRepo();

        // Beispielprodukt erstellen
        Product product = new Product("1", "Apfel");

        // Beispielorder mit Produkt, Status und aktuellem Zeitstempel anlegen
        sampleOrder = new Order("order1", List.of(product), OrderStatus.PROCESSING, Instant.now());

        // Beispielorder im Repository speichern
        repo.addOrder(sampleOrder);
    }

    // Testet, ob getOrders die gespeicherten Bestellungen korrekt zurückgibt
    @Test
    void getOrders() {
        // Alle Bestellungen aus dem Repository abrufen
        List<Order> actual = repo.getOrders();

        // Prüfen, ob genau eine Bestellung vorhanden ist
        assertEquals(1, actual.size());

        // Prüfen, ob die zurückgegebene Bestellung der Beispielbestellung entspricht
        assertEquals(sampleOrder, actual.get(0));
    }

    // Testet, ob getOrderById eine bestehende Bestellung korrekt findet
    @Test
    void getOrderById_existingId_returnsOrder() {
        // Bestellung mit ID "order1" abrufen
        Order actual = repo.getOrderById("order1");

        // Prüfen, ob die gefundene Bestellung der Beispielbestellung entspricht
        assertEquals(sampleOrder, actual);
    }

    // Testet, ob getOrderById bei nicht existierender ID null zurückgibt
    @Test
    void getOrderById_nonExistingId_returnsNull() {
        // Bestellung mit einer nicht existierenden ID abrufen
        Order actual = repo.getOrderById("nonexistent");

        // Prüfen, ob null zurückgegeben wird
        assertNull(actual);
    }

    // Testet, ob addOrder eine Bestellung korrekt hinzufügt
    @Test
    void addOrder_addsOrderSuccessfully() {
        // Neues Produkt für die Bestellung erstellen
        Product product = new Product("2", "Birne");

        // Neue Bestellung mit dem Produkt anlegen
        Order newOrder = new Order("order2", List.of(product), OrderStatus.PROCESSING, Instant.now());

        // Bestellung zum Repository hinzufügen
        Order actual = repo.addOrder(newOrder);

        // Prüfen, ob die zurückgegebene Bestellung der neuen Bestellung entspricht
        assertEquals(newOrder, actual);

        // Prüfen, ob die neue Bestellung im Repository gefunden werden kann
        assertEquals(newOrder, repo.getOrderById("order2"));
    }

    // Testet, ob removeOrder eine bestehende Bestellung löscht
    @Test
    void removeOrder_existingId_removesOrder() {
        // Beispielbestellung entfernen
        repo.removeOrder("order1");

        // Prüfen, ob die Bestellung nicht mehr gefunden wird
        assertNull(repo.getOrderById("order1"));

        // Prüfen, ob das Repository jetzt leer ist
        assertTrue(repo.getOrders().isEmpty());
    }

    // Testet, ob removeOrder bei nicht existierender ID keine Änderung vornimmt
    @Test
    void removeOrder_nonExistingId_noChange() {
        // Versuch, eine nicht existierende Bestellung zu entfernen
        repo.removeOrder("nonexistent");

        // Prüfen, ob die Anzahl der Bestellungen unverändert bleibt (1)
        assertEquals(1, repo.getOrders().size());
    }

    // Testet, ob updateOrder eine bestehende Bestellung aktualisiert
    @Test
    void updateOrder_existingOrder_updatesAndReturnsOrder() {
        // Beispielbestellung mit neuem Status erzeugen
        Order updatedOrder = sampleOrder.withStatus(OrderStatus.IN_DELIVERY);

        // Bestellung im Repository aktualisieren
        Order result = repo.updateOrder(updatedOrder);

        // Prüfen, ob die zurückgegebene Bestellung der aktualisierten entspricht
        assertEquals(updatedOrder, result);

        // Prüfen, ob die Bestellung im Repository jetzt den neuen Status hat
        assertEquals(OrderStatus.IN_DELIVERY, repo.getOrderById("order1").status());
    }

    // Testet, ob updateOrder bei nicht existierender Bestellung null zurückgibt
    @Test
    void updateOrder_nonExistingOrder_returnsNull() {
        // Neue Bestellung, die noch nicht im Repository ist
        Product product = new Product("3", "Orange");
        Order nonExisting = new Order("order3", List.of(product), OrderStatus.PROCESSING, Instant.now());

        // Versuch, die nicht existierende Bestellung zu aktualisieren
        Order result = repo.updateOrder(nonExisting);

        // Prüfen, ob null zurückgegeben wird (Update fehlgeschlagen)
        assertNull(result);
    }
}
