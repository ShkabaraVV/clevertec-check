package ru.clevertec.check.strategy;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountCardStrategyTest {

    @Test
    public void testCalculateDiscount_WithDiscountCardAndNonWholesale() {
        // Arrange
        Product product = new Product(1, "Test Product", new BigDecimal("100.00"), 10, false);
        int quantity = 3;
        DiscountCard discountCard = new DiscountCard(12345, 15.0);

        // Act
        BigDecimal discount = new DiscountCardStrategy().calculateDiscount(product, quantity, discountCard);

        // Assert
        assertEquals(new BigDecimal("45.0000"), discount); // 100.00 * 3 * 0.15 = 45.00
    }

    @Test
    public void testCalculateDiscount_WithoutDiscountCard() {
        // Arrange
        Product product = new Product(1, "Test Product", new BigDecimal("50.00"), 5, false);
        int quantity = 2;
        DiscountCard discountCard = null;

        // Act
        BigDecimal discount = new DiscountCardStrategy().calculateDiscount(product, quantity, discountCard);

        // Assert
        assertEquals(BigDecimal.ZERO, discount); // No discount card, so discount should be zero
    }

    @Test
    public void testCalculateDiscount_WholesaleProductAndEnoughQuantity() {
        // Arrange
        Product product = new Product(1, "Wholesale Product", new BigDecimal("200.00"), 20, true);
        int quantity = 5;
        DiscountCard discountCard = new DiscountCard(54321, 10.0);

        // Act
        BigDecimal discount = new DiscountCardStrategy().calculateDiscount(product, quantity, discountCard);

        // Assert
        assertEquals(BigDecimal.ZERO, discount); // Should not apply discount card discount for wholesale products
    }
}

