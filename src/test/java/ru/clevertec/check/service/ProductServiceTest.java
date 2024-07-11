package ru.clevertec.check.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.db.DatabaseConnection;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProductServiceTest {

    private DatabaseConnection dbConnection;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        dbConnection = mock(DatabaseConnection.class);
        productService = new ProductService(dbConnection);
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dbConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("description")).thenReturn("Milk").thenReturn("Pizza");
        when(resultSet.getBigDecimal("price")).thenReturn(BigDecimal.valueOf(1.00)).thenReturn(BigDecimal.valueOf(5.00));
        when(resultSet.getInt("quantity_in_stock")).thenReturn(10).thenReturn(20);
        when(resultSet.getBoolean("wholesale_product")).thenReturn(false).thenReturn(true);

        Map<Integer, Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertTrue(products.containsKey(1));
        assertTrue(products.containsKey(2));

        verify(connection).close();
        verify(statement).close();
        verify(resultSet).close();
    }

    @Test
    public void testGetProductById() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dbConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("description")).thenReturn("Milk");
        when(resultSet.getBigDecimal("price")).thenReturn(BigDecimal.valueOf(1.00));
        when(resultSet.getInt("quantity_in_stock")).thenReturn(10);
        when(resultSet.getBoolean("wholesale_product")).thenReturn(false);

        Product product = productService.getProductById(1);

        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Milk", product.getDescription());

        verify(connection).close();
        verify(statement).close();
        verify(resultSet).close();
    }

    @Test
    public void testGetProductByIdNotFound() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dbConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Product product = productService.getProductById(9999);

        assertNull(product);

        verify(connection).close();
        verify(statement).close();
        verify(resultSet).close();
    }
}

