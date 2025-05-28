package ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import db.DatabaseManager;

import javax.swing.*;

public class RegisterScreen {
    private static boolean isDarkMode = false;

    public static void show() {
        if (isDarkMode) FlatDarkLaf.setup();
        else FlatLightLaf.setup();

        JFrame frame = new JFrame("Register");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        JTextField userField = new JTextField();
        userField.setBounds(150, 50, 180, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 90, 80, 25);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 90, 180, 25);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setBounds(20, 130, 130, 25);
        JPasswordField confirmPassField = new JPasswordField();
        confirmPassField.setBounds(150, 130, 180, 25);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 170, 80, 25);
        String[] roles = {"admin", "student"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setBounds(150, 170, 180, 25);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 210, 180, 30);

        JButton loginButton = new JButton("Back to Login");
        loginButton.setBounds(150, 250, 180, 30);

        JButton toggleButton = new JButton("Toggle Theme");
        toggleButton.setBounds(150, 290, 180, 25);

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(confirmPassField.getPassword());
            String role = (String) roleCombo.getSelectedItem();

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = DatabaseManager.registerUser(username, password, role);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Registration successful!");
                frame.dispose();
                LoginScreen.main(null);
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginButton.addActionListener(e -> {
            frame.dispose();
            LoginScreen.show();
        });

        toggleButton.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            frame.dispose();
            show();
        });

        frame.add(userLabel); frame.add(userField);
        frame.add(passLabel); frame.add(passField);
        frame.add(confirmPassLabel); frame.add(confirmPassField);
        frame.add(roleLabel); frame.add(roleCombo);
        frame.add(registerButton); frame.add(loginButton);
        frame.add(toggleButton);

        frame.setVisible(true);
    }
}
