package ru.clevertec.check.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    public void testProductConstructorAndGetters() {
        Product product = new Product(1, "Test Product", new BigDecimal("99.99"), 10, true);

        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getDescription());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals(10, product.getQuantityInStock());
        assertTrue(product.isWholesale());
    }

    @Test
    public void testProductSetters() {
        Product product = new Product();

        product.setId(2);
        product.setDescription("Another Product");
        product.setPrice(new BigDecimal("49.99"));
        product.setQuantityInStock(5);
        product.setWholesale(false);

        assertEquals(2, product.getId());
        assertEquals("Another Product", product.getDescription());
        assertEquals(new BigDecimal("49.99"), product.getPrice());
        assertEquals(5, product.getQuantityInStock());
        assertFalse(product.isWholesale());
    }
}

