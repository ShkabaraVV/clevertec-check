package ru.clevertec.check.service;

import ru.clevertec.check.db.DatabaseConnection;
import ru.clevertec.check.model.DiscountCard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountService {
    private DatabaseConnection dbConnection;

    public DiscountService(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DiscountCard getDiscountCard(int cardNumber) {
        String query = "SELECT id, number, amount FROM public.discount_card WHERE number = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, cardNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int number = resultSet.getInt("number");
                    double amount = resultSet.getDouble("amount");

                    return new DiscountCard(cardNumber, amount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}