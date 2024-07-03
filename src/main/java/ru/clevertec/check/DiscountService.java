package ru.clevertec.check;

import java.util.Map;

public class DiscountService {
    private static DiscountService instance;
    private Map<Integer, DiscountCard> discountCards;

    private DiscountService() {
        discountCards = CSVReader.readDiscountCards("./src/main/resources/discountCards.csv");
    }

    public static DiscountService getInstance() {
        if (instance == null) {
            instance = new DiscountService();
        }
        return instance;
    }

    public DiscountCard getDiscountCard(int cardNumber) {
        return discountCards.get(cardNumber);
    }
}
