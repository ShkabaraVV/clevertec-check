package ru.clevertec.check.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    private static final String MOCK_URL = "jdbc:postgresql://localhost:5432/check";
    private static final String MOCK_USERNAME = "postgres";
    private static final String MOCK_PASSWORD = "1";

    private DatabaseConnection databaseConnection;

    @BeforeEach
    public void setUp() {
        // Создаем экземпляр класса DatabaseConnection перед каждым тестом
        databaseConnection = new DatabaseConnection(MOCK_URL, MOCK_USERNAME, MOCK_PASSWORD);
    }

    @Test
    public void testGetConnection() throws SQLException {
        Connection conn = databaseConnection.getConnection();
        assertNotNull(conn);
        // Проверяем, что параметры соединения правильные
        assertEquals(MOCK_URL, conn.getMetaData().getURL());
        assertEquals(MOCK_USERNAME, conn.getMetaData().getUserName());
    }

    @Test
    public void testGetConnectionWithInvalidUrl() {
        // Подставляем неверный URL для вызова getConnection
        DatabaseConnection conn = new DatabaseConnection("invalid_url", MOCK_USERNAME, MOCK_PASSWORD);
        assertThrows(SQLException.class, () -> {
            conn.getConnection();
        });
    }

    @Test
    public void testGetConnectionWithInvalidCredentials() {
        // Подставляем неверные учетные данные для вызова getConnection
        DatabaseConnection conn = new DatabaseConnection(MOCK_URL, "invalid_username", "invalid_password");
        assertThrows(SQLException.class, () -> {
            conn.getConnection();
        });
    }
}

