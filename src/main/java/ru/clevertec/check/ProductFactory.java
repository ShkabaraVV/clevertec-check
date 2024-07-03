package ru.clevertec.check;

import java.math.BigDecimal;

public class ProductFactory {
    public static Product createProduct(int id, String description , BigDecimal price, boolean isWholesale) {
        return new Product(id, description, price, isWholesale);
    }
}
