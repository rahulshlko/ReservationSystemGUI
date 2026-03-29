package com.reservation.gui;

import com.reservation.db.Database;
import com.reservation.model.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ReservationPanel extends JPanel {

    private final Database db;
    private final Runnable onReserved;

    private JTextField  nameField, trainField, trainNameField, dateField, fromField, toField;
    private JComboBox<String> classCombo;
    private JLabel      pnrLabel;

    private static final String[] CLASS_TYPES = {
        "1A - First AC", "2A - Second AC", "3A - Third AC",
        "SL - Sleeper",  "CC - Chair Car", "GN - General"
    };

    public ReservationPanel(Database db, Runnable onReserved) {
        this.db         = db;
        this.onReserved = onReserved;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // ---- Title ----
        JLabel title = new JLabel("Module 2 — Reservation Form");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(new Color(30, 80, 160));
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // ---- Form ----
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        nameField     = new JTextField(20);
        trainField    = new JTextField(20);
        trainNameField = new JTextField(20);
        trainNameField.setEditable(false);
        trainNameField.setBackground(new Color(240, 240, 240));
        classCombo    = new JComboBox<>(CLASS_TYPES);
        dateField     = new JTextField(20);
        fromField     = new JTextField(20);
        toField       = new JTextField(20);

        // Auto-fill train name on focus lost
        trainField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                String name = db.getTrainName(trainField.getText().trim());
                trainNameField.setText(name.isEmpty() ? "Not found — enter manually" : name);
                trainNameField.setEditable(name.isEmpty());
                trainNameField.setBackground(name.isEmpty() ? Color.WHITE : new Color(240, 240, 240));
            }
        });

        addRow(form, gbc, 0, "Passenger Name:",  nameField);
        addRow(form, gbc, 1, "Train Number:",    trainField);
        addRow(form, gbc, 2, "Train Name:",      trainNameField);
        addRow(form, gbc, 3, "Class Type:",      classCombo);
        addRow(form, gbc, 4, "Date (DD/MM/YYYY):", dateField);
        addRow(form, gbc, 5, "From (Place):",    fromField);
        addRow(form, gbc, 6, "To (Destination):", toField);

        // PNR result label
        pnrLabel = new JLabel(" ");
        pnrLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        pnrLabel.setForeground(new Color(0, 130, 50));
        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        form.add(pnrLabel, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton reserveBtn = makeButton("Reserve", new Color(30, 80, 160));
        JButton clearBtn   = makeButton("Clear",   new Color(130, 130, 130));

        reserveBtn.addActionListener(e -> handleReserve());
        clearBtn.addActionListener(e -> clearFields());

        btnPanel.add(reserveBtn);
        btnPanel.add(clearBtn);

        gbc.gridy = 8; gbc.insets = new Insets(15, 8, 5, 8);
        form.add(btnPanel, gbc);

        add(form, BorderLayout.CENTER);
    }

    private void handleReserve() {
        String name     = nameField.getText().trim();
        String trainNum = trainField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = (String) classCombo.getSelectedItem();
        String date     = dateField.getText().trim();
        String from     = fromField.getText().trim();
        String to       = toField.getText().trim();

        if (name.isEmpty() || trainNum.isEmpty() || date.isEmpty() || from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (trainName.isEmpty() || trainName.equals("Not found — enter manually")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Train Name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirm Reservation?\n\n" +
            "Passenger : " + name + "\n" +
            "Train     : " + trainNum + " - " + trainName + "\n" +
            "Class     : " + classType + "\n" +
            "Date      : " + date + "\n" +
            "Route     : " + from + " → " + to,
            "Confirm Booking", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        String pnr = db.generatePNR();
        db.addReservation(new Reservation(pnr, name, trainNum, trainName, classType, date, from, to));
        pnrLabel.setText("✔ Reservation Successful!  PNR: " + pnr);
        if (onReserved != null) onReserved.run();
        JOptionPane.showMessageDialog(this,
            "Reservation confirmed!\nYour PNR: " + pnr + "\nSave this for future reference.",
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        nameField.setText(""); trainField.setText(""); trainNameField.setText("");
        dateField.setText(""); fromField.setText(""); toField.setText("");
        classCombo.setSelectedIndex(0);
        pnrLabel.setText(" ");
    }

    private void addRow(JPanel p, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridy = row; gbc.gridx = 0; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        p.add(l, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        if (field instanceof JTextField) styleField((JTextField) field);
        p.add(field, gbc);
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            new EmptyBorder(5, 8, 5, 8)
        ));
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
