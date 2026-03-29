# ReservationSystem — Swing GUI

A clean Java Swing desktop application covering all three modules of an Online Reservation System: **Login**, **Reservation**, and **Cancellation**.

---

## Project Structure

```
ReservationSystem/
├── README.md
├── bin/                                         ← Compiled .class files (auto-generated)
└── src/
    └── com/
        └── reservation/
            ├── main/
            │   └── App.java                     ← Entry point
            ├── model/
            │   ├── Reservation.java             ← Booking data model
            │   └── User.java                    ← User data model
            ├── db/
            │   └── Database.java                ← In-memory data store
            └── gui/
                ├── LoginPanel.java              ← Login window (Module 1)
                ├── ReservationPanel.java        ← Reservation form (Module 2)
                ├── CancellationPanel.java       ← Cancellation form (Module 3)
                ├── ViewPanel.java               ← All reservations table
                └── MainWindow.java              ← Main app window with sidebar
```

---

## How to Compile & Run

### Step 1 — Compile (from inside ReservationSystem/)
```bash
javac -d bin \
  src/com/reservation/model/*.java \
  src/com/reservation/db/*.java \
  src/com/reservation/gui/*.java \
  src/com/reservation/main/*.java
```

> **Windows users:** replace `\` with `^` or run each line separately.

### Step 2 — Run
```bash
java -cp bin com.reservation.main.App
```

---

## Default Login Credentials

| Username | Password |
|----------|----------|
| admin    | admin123 |
| Rahul    | rahul123 |    

---

## Sample Train Numbers

| Train No. | Train Name         |
|-----------|--------------------|
| 12301     | Gorakhpur Express   |
| 12951     | Mumbai Rajdhani    |
| 22221     | Delhi Express    |
| 12002     | Lucknow Shatabdi    |
| 12259     | Garib Rath Express |
| 12560     | Shivganga Express  |

---

## Modules

| Module | GUI File | Description |
|--------|----------|-------------|
| Login       | `LoginPanel.java`       | Username/password login with 3 attempts |
| Reservation | `ReservationPanel.java` | Form to book a ticket and get PNR |
| Cancellation| `CancellationPanel.java`| Search by PNR and cancel booking |
| View All    | `ViewPanel.java`        | Table of all active reservations |

---

## Requirements

- Java 8 or above (Swing is bundled — no extra libraries needed)

---


