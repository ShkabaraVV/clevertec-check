package ru.clevertec.check;

import java.util.Map;

public class ProductService {
    private static ProductService instance;
    private Map<Integer, Product> products;

    private ProductService() {
        products = CSVReader.readProducts("./src/main/resources/products.csv");
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public Product getProductById(int id) {
        return products.get(id);
    }
}
