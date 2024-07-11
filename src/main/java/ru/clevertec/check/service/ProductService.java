package ru.clevertec.check.service;

import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.model.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductService {
    private Map<Integer, Product> products = new HashMap<>();

    public ProductService(String pathToFile) {
        loadProductsFromFile(pathToFile);
    }

    private void loadProductsFromFile(String pathToFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(";");
                int id = Integer.parseInt(values[0]);
                String description = values[1];
                BigDecimal price = new BigDecimal(values[2]);
                int quantityInStock = Integer.parseInt(values[3]);
                boolean wholesaleProduct = Boolean.parseBoolean(values[4]);
                products.put(id, new Product(id, description, price, quantityInStock, wholesaleProduct));
            }
        } catch (IOException e) {
            throw new InternalServerErrorException("INTERNAL SERVER ERROR", e);
        }
    }

    public Product getProductById(int id) {
        return products.get(id);
    }
}
