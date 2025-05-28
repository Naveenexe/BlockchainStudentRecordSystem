package ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import model.Block;
import model.Blockchain;
import util.PDFExporter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class BlockchainApp {

    private static boolean isDarkTheme = false;

    public static void launchApp(String role) {
        setTheme();

        JFrame frame = new JFrame("Blockchain Exam System - " + role.toUpperCase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JButton toggleTheme = new JButton("🌓 Toggle Theme");
        toggleTheme.setBounds(640, 20, 120, 25);

        JLabel header = new JLabel("Student Record Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBounds(250, 10, 300, 30);
        frame.add(header);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 60, 80, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 60, 150, 25);

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(30, 100, 80, 25);
        JTextField subjectField = new JTextField();
        subjectField.setBounds(100, 100, 150, 25);

        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setBounds(30, 140, 80, 25);
        JTextField marksField = new JTextField();
        marksField.setBounds(100, 140, 150, 25);

        JButton addBtn = new JButton("➕ Add Result");
        addBtn.setBounds(270, 60, 150, 30);

        JButton exportBtn = new JButton("📄 Export PDF");
        exportBtn.setBounds(270, 100, 150, 30);

        JButton verifyBtn = new JButton("🔐 Verify Blockchain");
        verifyBtn.setBounds(270, 140, 180, 30);

        String[] columns = {"Name", "Subject", "Marks", "Hash", "Prev Hash"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 200, 720, 150);

        JPanel chartPanel = new JPanel();
        chartPanel.setBounds(30, 370, 720, 180);
        chartPanel.setLayout(new BorderLayout());
        frame.add(chartPanel);

        if ("student".equalsIgnoreCase(role)) {
            addBtn.setEnabled(false);
            exportBtn.setEnabled(false);
        }

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String subject = subjectField.getText();
                int marks = Integer.parseInt(marksField.getText());
                Blockchain.addBlock(name, subject, marks);
                Block latest = Blockchain.getChain().get(Blockchain.getChain().size() - 1);
                model.addRow(new Object[]{latest.getStudentName(), latest.getSubject(), latest.getMarks(), latest.getHash(), latest.getPreviousHash()});
                updateChart(chartPanel, Blockchain.getChain());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid marks!");
            }
        });

        exportBtn.addActionListener(e -> {
            PDFExporter.export(Blockchain.getChain(), "ExamBlockchain.pdf");
            JOptionPane.showMessageDialog(frame, "PDF Exported Successfully!");
        });

        verifyBtn.addActionListener(e -> {
            boolean valid = Blockchain.isChainValid(model, table);
            if (valid) {
                JOptionPane.showMessageDialog(frame, "Blockchain is VALID ✅");
            }
        });


        toggleTheme.addActionListener(e -> {
            isDarkTheme = !isDarkTheme;
            frame.dispose();
            launchApp(role); // restart UI with new theme
        });

        frame.add(toggleTheme);
        frame.add(nameLabel); frame.add(nameField);
        frame.add(subjectLabel); frame.add(subjectField);
        frame.add(marksLabel); frame.add(marksField);
        frame.add(addBtn); frame.add(exportBtn); frame.add(verifyBtn);
        frame.add(scrollPane);

        updateChart(chartPanel, Blockchain.getChain());
        frame.setVisible(true);
    }

    private static void updateChart(JPanel panel, List<Block> chain) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Block b : chain) {
            dataset.addValue(b.getMarks(), b.getStudentName(), b.getSubject());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Student Marks", "Subject", "Marks",
                dataset
        );
        panel.removeAll();
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        panel.validate();
    }

    private static void setTheme() {
        try {
            if (isDarkTheme) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }
}
