package com.reservation.main;

import com.reservation.db.Database;
import com.reservation.gui.LoginPanel;
import com.reservation.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the Online Reservation System (Swing GUI).
 *
 * Compile:
 *   javac -d bin src/com/reservation/model/*.java \
 *               src/com/reservation/db/*.java \
 *               src/com/reservation/gui/*.java \
 *               src/com/reservation/main/*.java
 *
 * Run:
 *   java -cp bin com.reservation.main.App
 */
public class App {

    public static void main(String[] args) {
        // Use system look and feel for native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            Database db = new Database();
            db.init();

            // ---- Login window ----
            JFrame loginFrame = new JFrame("Login — Reservation System");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(450, 340);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setResizable(false);

            LoginPanel loginPanel = new LoginPanel(db, () -> {
                loginFrame.dispose();
                // ---- Main window ----
                MainWindow mainWindow = new MainWindow(db);
                mainWindow.setVisible(true);
            });

            loginFrame.setContentPane(loginPanel);
            loginFrame.setVisible(true);
        });
    }
}
