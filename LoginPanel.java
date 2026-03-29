package com.reservation.gui;

import com.reservation.db.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {

    private final Database db;
    private final Runnable onLoginSuccess;

    private JTextField     userField;
    private JPasswordField passField;
    private JLabel         statusLabel;
    private int            attempts = 3;

    public LoginPanel(Database db, Runnable onLoginSuccess) {
        this.db             = db;
        this.onLoginSuccess = onLoginSuccess;
        buildUI();
    }

    private void buildUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.gridx  = 0;

        // Title
        JLabel title = new JLabel("Online Reservation System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 80, 160));
        gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);

        JLabel sub = new JLabel("Please login to continue", SwingConstants.CENTER);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(Color.GRAY);
        gbc.gridy = 1;
        card.add(sub, gbc);

        card.add(Box.createVerticalStrut(10), withRow(gbc, 2));

        // Username
        gbc.gridwidth = 1; gbc.gridy = 3; gbc.gridx = 0;
        card.add(makeLabel("Username:"), gbc);
        userField = new JTextField(18);
        styleField(userField);
        gbc.gridx = 1;
        card.add(userField, gbc);

        // Password
        gbc.gridy = 4; gbc.gridx = 0;
        card.add(makeLabel("Password:"), gbc);
        passField = new JPasswordField(18);
        styleField(passField);
        gbc.gridx = 1;
        card.add(passField, gbc);

        // Status
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        card.add(statusLabel, gbc);

        // Login button
        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(30, 80, 160));
        gbc.gridy = 6;
        card.add(loginBtn, gbc);

        // Hint
        JLabel hint = new JLabel("Default: admin / admin123", SwingConstants.CENTER);
        hint.setFont(new Font("SansSerif", Font.ITALIC, 10));
        hint.setForeground(Color.GRAY);
        gbc.gridy = 7;
        card.add(hint, gbc);

        loginBtn.addActionListener(e -> handleLogin());
        passField.addActionListener(e -> handleLogin());

        add(card);
    }

    private void handleLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (db.validateUser(user, pass)) {
            onLoginSuccess.run();
        } else {
            attempts--;
            if (attempts > 0)
                statusLabel.setText("Invalid credentials. Attempts left: " + attempts);
            else {
                statusLabel.setText("Too many failed attempts!");
                JOptionPane.showMessageDialog(this,
                    "Too many failed login attempts. Exiting.",
                    "Access Denied", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            passField.setText("");
        }
    }

    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return l;
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            new EmptyBorder(5, 8, 5, 8)
        ));
    }

    private void styleButton(JButton b, Color bg) {
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 20, 10, 20));
        b.setOpaque(true);
    }

    private GridBagConstraints withRow(GridBagConstraints gbc, int row) {
        gbc.gridy = row; gbc.gridwidth = 2; gbc.gridx = 0;
        return gbc;
    }
}
