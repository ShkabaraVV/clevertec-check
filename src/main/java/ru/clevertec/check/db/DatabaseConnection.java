package ru.clevertec.check.db;

import lombok.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
public class DatabaseConnection {
    private String url;
    private String username;
    private String password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
