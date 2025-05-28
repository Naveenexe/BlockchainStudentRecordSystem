package model;

import util.Database;
import util.Hasher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    public static void addBlock(String studentName, String subject, int marks) {
        try (Connection conn = Database.getConnection()) {
            String prevHash = getLastHash(conn);
            String hash = Hasher.generateHash(studentName + subject + marks + prevHash);

            String sql = "INSERT INTO blocks (student_name, subject, marks, previous_hash, hash) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentName);
            ps.setString(2, subject);
            ps.setInt(3, marks);
            ps.setString(4, prevHash);
            ps.setString(5, hash);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getLastHash(Connection conn) throws SQLException {
        String sql = "SELECT hash FROM blocks ORDER BY id DESC LIMIT 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs.next() ? rs.getString("hash") : "0";
    }

    public static List<Block> getChain() {
        List<Block> chain = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT student_name, subject, marks, previous_hash, hash FROM blocks ORDER BY id ASC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                chain.add(new Block(
                        rs.getString("student_name"),
                        rs.getString("subject"),
                        rs.getInt("marks"),
                        rs.getString("previous_hash")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chain;
    }

    public static boolean isChainValid(DefaultTableModel model, JTable table) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT id, student_name, subject, marks, previous_hash, hash FROM blocks ORDER BY id ASC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String previousHash = "0";
            int row = 0;

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("student_name");
                String subject = rs.getString("subject");
                int marks = rs.getInt("marks");
                String storedPrevHash = rs.getString("previous_hash");
                String storedHash = rs.getString("hash");

                String calculatedHash = Hasher.generateHash(name + subject + marks + storedPrevHash);

                if (!storedPrevHash.equals(previousHash) || !storedHash.equals(calculatedHash)) {
                    // Log tampered block
                    String logEntry = String.format("Tampered at Block ID: %d | Name: %s | Subject: %s", id, name, subject);

                    try (BufferedWriter writer = Files.newBufferedWriter(
                            Paths.get("tampered_log.txt"),
                            StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND)) {
                        writer.write(logEntry);
                        writer.newLine();
                    }

                    // Highlight the row in red
                    table.setRowSelectionInterval(row, row);
                    table.setSelectionBackground(Color.RED);

                    JOptionPane.showMessageDialog(null,
                            "Blockchain is tampered at Block ID " + id +
                                    "\nReason: Hash mismatch or wrong previous hash." +
                                    "\nCheck tampered_log.txt for details.",
                            "Blockchain Verification Failed", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                previousHash = storedHash;
                row++;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
