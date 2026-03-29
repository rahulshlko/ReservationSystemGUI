package com.reservation.gui;

import com.reservation.db.Database;
import com.reservation.model.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CancellationPanel extends JPanel {

    private final Database db;
    private final Runnable onCancelled;

    private JTextField pnrField;
    private JTextArea  detailsArea;

    public CancellationPanel(Database db, Runnable onCancelled) {
        this.db          = db;
        this.onCancelled = onCancelled;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // ---- Title ----
        JLabel title = new JLabel("Module 3 — Cancellation Form");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(new Color(30, 80, 160));
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // ---- Card ----
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(25, 35, 25, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // PNR input row
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel lbl = new JLabel("Enter PNR Number:");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        card.add(lbl, gbc);

        pnrField = new JTextField(18);
        pnrField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        pnrField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1; gbc.weightx = 1;
        card.add(pnrField, gbc);

        // Search button
        JButton searchBtn = makeButton("Search", new Color(30, 80, 160));
        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 2;
        card.add(searchBtn, gbc);

        // Details area
        detailsArea = new JTextArea(8, 40);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsArea.setBackground(new Color(248, 248, 248));
        detailsArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        detailsArea.setText("Enter a PNR number and click Search to view details.");

        JScrollPane scroll = new JScrollPane(detailsArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridy = 2; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1;
        card.add(scroll, gbc);

        // Cancel button
        JButton cancelBtn = makeButton("Cancel Reservation", new Color(190, 50, 50));
        cancelBtn.setEnabled(false);
        gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0;
        gbc.insets = new Insets(12, 8, 8, 8);
        card.add(cancelBtn, gbc);

        // Wire up search
        searchBtn.addActionListener(e -> {
            String pnr = pnrField.getText().trim().toUpperCase();
            Reservation r = db.getReservation(pnr);
            if (r == null) {
                detailsArea.setText("No reservation found for PNR: " + pnr);
                cancelBtn.setEnabled(false);
            } else {
                detailsArea.setText(
                    "PNR Number   : " + r.getPnr()           + "\n" +
                    "Passenger    : " + r.getPassengerName() + "\n" +
                    "Train No.    : " + r.getTrainNumber()   + "\n" +
                    "Train Name   : " + r.getTrainName()     + "\n" +
                    "Class        : " + r.getClassType()     + "\n" +
                    "Date         : " + r.getDateOfJourney() + "\n" +
                    "From         : " + r.getFromPlace()     + "\n" +
                    "To           : " + r.getDestination()
                );
                cancelBtn.setEnabled(true);
            }
        });

        // Wire up cancel
        cancelBtn.addActionListener(e -> {
            String pnr = pnrField.getText().trim().toUpperCase();
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel PNR: " + pnr + "?\nThis action cannot be undone.",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                db.removeReservation(pnr);
                detailsArea.setText("Reservation " + pnr + " has been successfully cancelled.");
                cancelBtn.setEnabled(false);
                pnrField.setText("");
                if (onCancelled != null) onCancelled.run();
                JOptionPane.showMessageDialog(this,
                    "Reservation cancelled successfully.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(card, BorderLayout.CENTER);
    }

    private JButton makeButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(new EmptyBorder(9, 25, 9, 25));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
