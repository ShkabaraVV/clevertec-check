package ru.clevertec.check.strategy;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WholesaleDiscountStrategyTest {

    @Test
    public void testCalculateDiscount_WholesaleProductAndEnoughQuantity() {
        // Arrange
        Product product = new Product(1, "Wholesale Product", new BigDecimal("200.00"), 10, true);
        int quantity = 5;
        DiscountCard discountCard = new DiscountCard(54321, 15.0);

        // Act
        BigDecimal discount = new WholesaleDiscountStrategy().calculateDiscount(product, quantity, discountCard);

        // Assert
        assertEquals(new BigDecimal("100.000"), discount); // 200.00 * 5 * 0.1 = 100.00
    }

    @Test
    public void testCalculateDiscount_NonWholesaleProduct() {
        // Arrange
        Product product = new Product(1, "Regular Product", new BigDecimal("50.00"), 5, false);
        int quantity = 3;
        DiscountCard discountCard = new DiscountCard(98765, 10.0);

        // Act
        BigDecimal discount = new WholesaleDiscountStrategy().calculateDiscount(product, quantity, discountCard);

        // Assert
        assertEquals(BigDecimal.ZERO, discount); // Should not apply wholesale discount for non-wholesale products
    }

    @Test
    public void testCalculateDiscount_InsufficientQuantity() {
        // Arrange
        Product product = new Product(1, "Wholesale Product", new BigDecimal("300.00"), 4, true);
        int quantity = 4;
        DiscountCard discountCard = new DiscountCard(24680, 20.0);

        // Act
        BigDecimal discount = new WholesaleDiscountStrategy().calculateDiscount(product, quantity, discountCard);

        // Assert
        assertEquals(BigDecimal.ZERO, discount); // Should not apply wholesale discount for less than 5 quantity
    }
}
