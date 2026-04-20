package org.example.week4_assignment.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3307/shopping_cart_localization");
    private static final String USER =
            System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD =
            System.getenv().getOrDefault("DB_PASSWORD", "");

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}