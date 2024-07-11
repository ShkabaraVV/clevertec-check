package ru.clevertec.check;

import ru.clevertec.check.db.DatabaseConnection;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.DiscountService;
import ru.clevertec.check.service.ProductService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckRunner {
    public static void main(String[] args) {
        String saveToFile = "result.csv";
        String dbUrl = null;
        String dbUsername = null;
        String dbPassword = null;

        for (String arg : args) {
            if (arg.startsWith("saveToFile=")) {
                saveToFile = arg.split("=")[1];
            } else if (arg.startsWith("datasource.url=")) {
                dbUrl = arg.split("=")[1];
            } else if (arg.startsWith("datasource.username=")) {
                dbUsername = arg.split("=")[1];
            } else if (arg.startsWith("datasource.password=")) {
                dbPassword = arg.split("=")[1];
            }
        }

        if (dbUrl == null || dbUsername == null || dbPassword == null) {
            System.err.println("Database connection parameters are missing.");
            return;
        }
        DatabaseConnection dbConnection = new DatabaseConnection(dbUrl, dbUsername, dbPassword);
        executeSqlFromFile(dbConnection, "src/main/resources/data.sql");

        ProductService productService = new ProductService(dbConnection);
        DiscountService discountService = new DiscountService(dbConnection);
        CheckService checkService = new CheckService(productService, discountService);

        checkService.generateReceipt(args, saveToFile);
    }

    private static void executeSqlFromFile(DatabaseConnection dbConnection, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line.trim());
                if (line.trim().endsWith(";")) {
                    statement.execute(sql.toString());
                    sql.setLength(0);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}