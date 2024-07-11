package ru.clevertec.check.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.db.DatabaseConnection;
import ru.clevertec.check.model.DiscountCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountServiceTest {

    private DatabaseConnection dbConnection;
    private DiscountService discountService;

    @BeforeEach
    public void setUp() {
        dbConnection = mock(DatabaseConnection.class);
        discountService = new DiscountService(dbConnection);
    }

    @Test
    public void testGetDiscountCard() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dbConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("number")).thenReturn(1111);
        when(resultSet.getDouble("amount")).thenReturn(3.0);

        DiscountCard discountCard = discountService.getDiscountCard(1111);

        assertNotNull(discountCard);
        assertEquals(1111, discountCard.getCardNumber());
        assertEquals(3.0, discountCard.getDiscountRate());

        verify(connection).close();
        verify(statement).close();
        verify(resultSet).close();
    }

    @Test
    public void testGetDiscountCardNotFound() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dbConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        DiscountCard discountCard = discountService.getDiscountCard(9999);

        assertNull(discountCard);

        verify(connection).close();
        verify(statement).close();
        verify(resultSet).close();
    }
}

