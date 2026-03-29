package com.reservation.gui;

import com.reservation.db.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {

    private final Database db;
    private ViewPanel viewPanel;

    public MainWindow(Database db) {
        this.db = db;
        buildWindow();
    }

    private void buildWindow() {
        setTitle("Online Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        // ---- Sidebar ----
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(25, 65, 140));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(30, 0, 20, 0));

        JLabel appName = new JLabel("<html><center>Reservation<br>System</center></html>", SwingConstants.CENTER);
        appName.setFont(new Font("SansSerif", Font.BOLD, 16));
        appName.setForeground(Color.WHITE);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        appName.setBorder(new EmptyBorder(0, 10, 25, 10));
        sidebar.add(appName);

        // ---- Content panel with CardLayout ----
        JPanel content = new JPanel(new CardLayout());

        viewPanel = new ViewPanel(db);

        ReservationPanel  reservePanel  = new ReservationPanel(db,  () -> viewPanel.refresh());
        CancellationPanel cancelPanel   = new CancellationPanel(db, () -> viewPanel.refresh());

        content.add(reservePanel,  "reserve");
        content.add(cancelPanel,   "cancel");
        content.add(viewPanel,     "view");

        CardLayout cl = (CardLayout) content.getLayout();

        // Nav buttons
        String[] labels = { "🎫  New Reservation", "✖  Cancel Booking", "📋  View All" };
        String[] cards  = { "reserve", "cancel", "view" };

        JButton[] navBtns = new JButton[labels.length];
        for (int i = 0; i < labels.length; i++) {
            JButton btn = makeSidebarBtn(labels[i]);
            final String card = cards[i];
            final int idx = i;
            btn.addActionListener(e -> {
                cl.show(content, card);
                if (card.equals("view")) viewPanel.refresh();
                for (JButton b : navBtns) b.setBackground(new Color(25, 65, 140));
                navBtns[idx].setBackground(new Color(50, 100, 190));
            });
            navBtns[i] = btn;
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(4));
        }
        navBtns[0].setBackground(new Color(50, 100, 190)); // default selection

        // ---- Footer ----
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(25, 65, 140));
        JLabel footerLbl = new JLabel("Task 1 — Online Reservation System");
        footerLbl.setForeground(new Color(180, 200, 255));
        footerLbl.setFont(new Font("SansSerif", Font.ITALIC, 10));
        footer.add(footerLbl);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(footer);

        // ---- Assemble ----
        setLayout(new BorderLayout());
        add(sidebar,  BorderLayout.WEST);
        add(content,  BorderLayout.CENTER);
    }

    private JButton makeSidebarBtn(String text) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(200, 45));
        b.setPreferredSize(new Dimension(200, 45));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setBackground(new Color(25, 65, 140));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.PLAIN, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(new EmptyBorder(0, 20, 0, 0));
        return b;
    }
}
