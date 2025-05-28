package ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import db.DatabaseManager;

import javax.swing.*;

public class LoginScreen {
    private static boolean isDarkMode = false;

    public static void show() {
        if (isDarkMode) FlatDarkLaf.setup();
        else FlatLightLaf.setup();

        JFrame frame = new JFrame("Login");
        frame.setSize(400, 300);
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

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 130, 180, 30);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 170, 180, 30);

        JButton toggleButton = new JButton("Toggle Theme");
        toggleButton.setBounds(150, 210, 180, 25);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = DatabaseManager.loginUser(username, password);
            if (role != null) {
                frame.dispose();
                BlockchainApp.launchApp(role);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            RegisterScreen.show();
        });

        toggleButton.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            frame.dispose();
            show();
        });

        frame.add(userLabel); frame.add(userField);
        frame.add(passLabel); frame.add(passField);
        frame.add(loginButton); frame.add(registerButton);
        frame.add(toggleButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::show);
    }
}
