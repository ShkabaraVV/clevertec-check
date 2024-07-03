package ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
//чтение аргументов из командной строки (продуктвов, карты)
public class CSVReader {
    public static Map<Integer, Product> readProducts(String filePath) {
        Map<Integer, Product> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String description = values[1];
                BigDecimal price = new BigDecimal(values[2]);
                boolean isWholesale = Boolean.parseBoolean(values[3]);
                products.put(id, ProductFactory.createProduct(id, description, price, isWholesale));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Map<Integer, DiscountCard> readDiscountCards(String filePath) {
        Map<Integer, DiscountCard> discountCards = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int cardNumber = Integer.parseInt(values[0]);
                double discountRate = Double.parseDouble(values[1]);
                discountCards.put(cardNumber, DiscountCardFactory.createDiscountCard(cardNumber, discountRate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return discountCards;
    }
}
