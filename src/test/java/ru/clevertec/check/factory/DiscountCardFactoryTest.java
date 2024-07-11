package ru.clevertec.check.factory;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.DiscountCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountCardFactoryTest {

    @Test
    public void testCreateDiscountCard() {
        // Arrange
        int cardNumber = 12345;
        double discountRate = 0.1;

        // Act
        DiscountCard discountCard = DiscountCardFactory.createDiscountCard(cardNumber, discountRate);

        // Assert
        assertEquals(cardNumber, discountCard.getCardNumber());
        assertEquals(discountRate, discountCard.getDiscountRate());
    }
}

