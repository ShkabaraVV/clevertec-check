package ru.clevertec.check.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class DiscountCardTest {

    @Test
    public void testDiscountCardConstructorAndGetters() {
        DiscountCard card = new DiscountCard(123456, 10.0);

        assertEquals(123456, card.getCardNumber());
        assertEquals(10.0, card.getDiscountRate(), 0.001);
    }

    @Test
    public void testDiscountCardSetters() {
        DiscountCard card = new DiscountCard();

        card.setCardNumber(654321);
        card.setDiscountRate(5.5);

        assertEquals(654321, card.getCardNumber());
        assertEquals(5.5, card.getDiscountRate(), 0.001);
    }
}
