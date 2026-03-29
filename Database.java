package com.reservation.db;

import com.reservation.model.Reservation;
import com.reservation.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Map<String, User>        users        = new HashMap<>();
    private final Map<String, Reservation> reservations = new HashMap<>();
    private final Map<String, String>      trains       = new HashMap<>();
    private int pnrCounter = 1000;

    public void init() {
        users.put("admin", new User("admin", "admin123"));
        users.put("user1", new User("user1", "pass1"));

        trains.put("12301", "Rajdhani Express");
        trains.put("12951", "Mumbai Rajdhani");
        trains.put("22221", "Duronto Express");
        trains.put("12002", "Bhopal Shatabdi");
        trains.put("12259", "Garib Rath Express");
        trains.put("12560", "Shivganga Express");
    }

    public boolean validateUser(String username, String password) {
        User u = users.get(username);
        return u != null && u.getPassword().equals(password);
    }

    public String getTrainName(String trainNumber) {
        return trains.getOrDefault(trainNumber, "");
    }

    public Map<String, String> getAllTrains() { return trains; }

    public String generatePNR() { return "PNR" + (++pnrCounter); }

    public void addReservation(Reservation r)       { reservations.put(r.getPnr(), r); }
    public Reservation getReservation(String pnr)   { return reservations.get(pnr); }
    public boolean removeReservation(String pnr)    { return reservations.remove(pnr) != null; }
    public Collection<Reservation> getAllReservations() { return reservations.values(); }
    public boolean hasReservations()                { return !reservations.isEmpty(); }
}
