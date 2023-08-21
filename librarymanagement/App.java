package com.librarymanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class App extends JFrame implements ActionListener {
    private Connection connection;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public App(Connection connection) {
        super("Library Management System");
        this.connection = connection;


        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);


        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (username.equals("admin") && password.equals("admin")) {
            openAdminApp();
        } else {
            openUserApp();
        }
        dispose();
    }

    private void openAdminApp() {
        AdminApp adminApp = new AdminApp(connection);
        adminApp.setVisible(true);
    }

    private void openUserApp() {
        UserApp userApp = new UserApp(connection);
        userApp.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "SYSTEM";
                String password = "gnanasai";

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    App app = new App(connection);
                    app.setVisible(true);
                } catch (SQLException e) {
                    System.err.println("Failed to connect to the database!");
                    e.printStackTrace();
                }
            }
        });
    }
}
