package org.example.week4_assignment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    public Map<String, String> loadLanguage(String lang) {
        Map<String, String> map = new HashMap<>();
        String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                System.err.println("Database connection failed.");
                return map;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, lang);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        map.put(rs.getString("key"), rs.getString("value"));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Failed to load localization data: " + e.getMessage());
        }

        return map;
    }
}