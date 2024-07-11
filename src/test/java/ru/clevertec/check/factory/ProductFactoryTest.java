package ru.clevertec.check.factory;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductFactoryTest {

    @Test
    public void testCreateProduct() {
        // Arrange
        int id = 1;
        String description = "Sample Product";
        BigDecimal price = new BigDecimal("99.99");
        int quantityInStock = 10;
        boolean isWholesale = true;

        // Act
        Product product = ProductFactory.createProduct(id, description, price, quantityInStock, isWholesale);

        // Assert
        assertEquals(id, product.getId());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(quantityInStock, product.getQuantityInStock());
        assertEquals(isWholesale, product.isWholesale());
    }
}

