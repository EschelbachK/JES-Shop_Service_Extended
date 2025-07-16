package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    // Testet, ob getProducts() die korrekte Liste zurückgibt
    @Test
    void getProducts() {
        // GIVEN
        ProductRepo repo = new ProductRepo();

        // WHEN
        List<Product> actual = repo.getProducts();

        // THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel"));
        assertEquals(expected, actual);
    }

    // Testet, ob getProductById() ein vorhandenes Produkt als Optional zurückgibt
    @Test
    void getProductById_existingId_returnsProduct() {
        // GIVEN
        ProductRepo repo = new ProductRepo();

        // WHEN
        Optional<Product> actual = repo.getProductById("1");

        // THEN
        Product expected = new Product("1", "Apfel");
        assertTrue(actual.isPresent(), "Produkt sollte vorhanden sein");
        assertEquals(expected, actual.get());
    }

    // Testet, ob addProduct() ein neues Produkt hinzufügt und zurückgibt
    @Test
    void addProduct_addsProductSuccessfully() {
        // GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane");

        // WHEN
        Product actual = repo.addProduct(newProduct);

        // THEN
        Product expected = new Product("2", "Banane");
        assertEquals(expected, actual);

        Optional<Product> stored = repo.getProductById("2");
        assertTrue(stored.isPresent(), "Produkt sollte nach dem Hinzufügen vorhanden sein");
        assertEquals(expected, stored.get());
    }

    // Testet, ob removeProduct() ein Produkt entfernt und Optional.empty() zurückgibt
    @Test
    void removeProduct_existingId_removesProduct() {
        // GIVEN
        ProductRepo repo = new ProductRepo();

        // WHEN
        repo.removeProduct("1");

        // THEN
        Optional<Product> actual = repo.getProductById("1");
        assertTrue(actual.isEmpty(), "Produkt sollte entfernt sein");
    }
}
