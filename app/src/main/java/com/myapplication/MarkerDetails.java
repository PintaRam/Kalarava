package com.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class MarkerDetails {
    private double latitude;
    private double longitude;
    private String eventType;
    private String time;
    private String date;
    private String description;

    public MarkerDetails() {
        // Default constructor required for Firebase
    }

    public MarkerDetails(double latitude, double longitude, String eventType, String time, String date, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventType = eventType;
        this.time = time;
        this.date = date;
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
