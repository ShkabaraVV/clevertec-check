package ru.clevertec.check;

public class DiscountCardFactory {
    public static DiscountCard createDiscountCard(int cardNumber, double discountRate) {
        return new DiscountCard(cardNumber, discountRate);
    }
}
