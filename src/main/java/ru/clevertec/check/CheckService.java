package ru.clevertec.check;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CheckService {
    private ProductService productService = ProductService.getInstance();
    private DiscountService discountService = DiscountService.getInstance();

    public void generateReceipt(String[] args) {
        Map<Product, Integer> products = new HashMap<>();
        DiscountCard discountCard = null;
        BigDecimal total = BigDecimal.ZERO;
        // Получаем текущую дату и время
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Создаём формат для вывода (день-месяц-год и время)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy; HH:mm");
        // Преобразуем дату и время в строку в заданном формате
        String formattedDateTime = currentDateTime.format(formatter);
        // Обработка аргументов
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                int cardNumber = Integer.parseInt(arg.split("=")[1]);
                discountCard = discountService.getDiscountCard(cardNumber);
            } else if (arg.contains("-")) {
                String[] parts = arg.split("-");
                int productId = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                Product product = productService.getProductById(productId);
                products.put(product, quantity);
            }
        }

        // Подсчет суммы
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            if (product.isWholesale() && quantity >= 5) {
                productTotal = productTotal.multiply(BigDecimal.valueOf(0.9));
            }
            total = total.add(productTotal);
        }

        // Применение скидки по карте
        if (discountCard != null) {
            total = total.multiply(BigDecimal.valueOf(1 - discountCard.getDiscountRate() / 100));
        }

        // Вывод чека
        StringBuilder receipt = new StringBuilder();
        receipt.append("Data; Time\n");
        receipt.append(formattedDateTime + "\n\n");
        receipt.append("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            receipt.append(String.format("%d;%s;%.2f\n", quantity, product.getDescription (), product.getPrice().multiply(BigDecimal.valueOf(quantity))));
        }
        receipt.append(String.format("Итого: %.2f\n", total));

        System.out.println(receipt.toString());

        // Запись в CSV
        try (FileWriter writer = new FileWriter("result.csv")) {
            writer.append(receipt.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
