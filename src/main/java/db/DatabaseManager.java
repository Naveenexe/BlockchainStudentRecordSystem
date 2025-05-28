package db;

import util.Database;
import java.sql.*;

public class DatabaseManager {
    public static boolean registerUser(String username, String password, String role) {
        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // Username already exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String loginUser(String username, String password) {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}