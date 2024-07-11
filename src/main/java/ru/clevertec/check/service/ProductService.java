package ru.clevertec.check.service;

import ru.clevertec.check.db.DatabaseConnection;
import ru.clevertec.check.model.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductService {
    private DatabaseConnection dbConnection;
    public ProductService(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    public Map<Integer, Product> getAllProducts() {
        Map<Integer, Product> products = new HashMap<>();
        String query = "SELECT id, description, price, quantity_in_stock, wholesale_product FROM public.product";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                BigDecimal price = resultSet.getBigDecimal("price");
                int quantityInStock = resultSet.getInt("quantity_in_stock");
                boolean wholesaleProduct = resultSet.getBoolean("wholesale_product");

                Product product = new Product(id, description, price, quantityInStock, wholesaleProduct);
                products.put(id, product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int productId) {
        String query = "SELECT id, description, price, quantity_in_stock, wholesale_product FROM public.product WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String description = resultSet.getString("description");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    int quantityInStock = resultSet.getInt("quantity_in_stock");
                    boolean wholesaleProduct = resultSet.getBoolean("wholesale_product");

                    return new Product(productId, description, price, quantityInStock, wholesaleProduct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}