package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/exam_blockchain";
    private static final String USER = "root";
    private static final String PASSWORD = "Admin@123";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("DB connection failed", e);
        }
    }
}