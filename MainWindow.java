package com.reservation.model;

public class Reservation {
    private String pnr;
    private String passengerName;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String fromPlace;
    private String destination;

    public Reservation(String pnr, String passengerName, String trainNumber,
                       String trainName, String classType, String dateOfJourney,
                       String fromPlace, String destination) {
        this.pnr           = pnr;
        this.passengerName = passengerName;
        this.trainNumber   = trainNumber;
        this.trainName     = trainName;
        this.classType     = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromPlace     = fromPlace;
        this.destination   = destination;
    }

    public String getPnr()           { return pnr; }
    public String getPassengerName() { return passengerName; }
    public String getTrainNumber()   { return trainNumber; }
    public String getTrainName()     { return trainName; }
    public String getClassType()     { return classType; }
    public String getDateOfJourney() { return dateOfJourney; }
    public String getFromPlace()     { return fromPlace; }
    public String getDestination()   { return destination; }

    public Object[] toTableRow() {
        return new Object[]{ pnr, passengerName, trainNumber, trainName,
                             classType, dateOfJourney, fromPlace, destination };
    }

    @Override
    public String toString() {
        return String.format(
            "PNR: %s | %s | %s - %s | %s | %s | %s → %s",
            pnr, passengerName, trainNumber, trainName,
            classType, dateOfJourney, fromPlace, destination
        );
    }
}
