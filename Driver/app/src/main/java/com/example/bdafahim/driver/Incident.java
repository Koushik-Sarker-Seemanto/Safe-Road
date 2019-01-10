package com.example.bdafahim.driver;

public class Incident {
    private int points;
    private String incidents;
    private double lati,longi;

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public Incident(int points, String incidents, double lati, double longi) {

        this.points = points;
        this.incidents = incidents;
        this.lati = lati;
        this.longi = longi;
    }

    public Incident(int points, String incidents) {
        this.points = points;
        this.incidents = incidents;
    }

    public Incident() {

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getIncidents() {
        return incidents;
    }

    public void setIncidents(String incidents) {
        this.incidents = incidents;
    }
}
