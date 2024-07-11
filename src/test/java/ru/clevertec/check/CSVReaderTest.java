package ru.clevertec.check;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.reader.DiscountCardCSVReader;
import ru.clevertec.check.reader.ProductCSVReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CSVReaderTest {

    @Test
    public void testReadProductCSV() throws IOException {
        String filePath = "src/main/resources/products.csv"; // Путь к тестовому CSV файлу с продуктами
        ProductCSVReader reader = new ProductCSVReader();

        Map<Integer, Product> products = reader.read(filePath);

        assertNotNull(products);
        assertEquals(20, products.size()); // Предположим, что в файле 3 строки с данными продуктов

        // Проверка наличия конкретного продукта с id=1
        assertTrue(products.containsKey(1));
        Product product1 = products.get(1);
        assertEquals("Milk", product1.getDescription());
        assertEquals(new BigDecimal("1.07"), product1.getPrice());
        assertEquals(10, product1.getQuantityInStock());
        assertTrue(product1.isWholesale());
    }

    @Test
    public void testReadDiscountCardCSV() throws IOException {
        String filePath = "src/main/resources/discountCards.csv"; // Путь к тестовому CSV файлу с картами скидок
        DiscountCardCSVReader reader = new DiscountCardCSVReader();

        Map<Integer, DiscountCard> cards = reader.read(filePath);

        assertNotNull(cards);
        assertEquals(4, cards.size());

        // Проверка наличия конкретной карты с id=2
        assertTrue(cards.containsKey(2));
        DiscountCard card2 = cards.get(2);
        assertEquals(2222, card2.getCardNumber()); // Так как в файле номера карт указаны как number, а не cardNumber
        assertEquals(3, card2.getDiscountRate(), 0.001);
    }
}



