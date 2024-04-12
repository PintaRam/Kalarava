package com.myapplication;

public class MarkerDetails {
    private String ename;
    private double latitude;
    private double longitude;
    private String eventType;
    private String startTime;
    private String endTime;
    private String startDate;

    private String endDate;
    private  String description;


    public MarkerDetails() {
        // Default constructor required for Firebase
    }

    public MarkerDetails(double latitude, double longitude, String eventType,String name, String startTime, String startDate,String endDate,String endTime,String description) {
        this.ename=name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventType = eventType;
        this.startTime =startTime;
        this.endTime = endTime;
        this.startDate = endDate;
        this.endDate =endDate;
        this.description = description;
    }

    public MarkerDetails( String eventType,String name, String startTime) {
        this.ename=name;
        this.eventType = eventType;
        this.startTime =startTime;

    }
    public  String getevent()
    {
        return this.ename;
    }

    public void  setEventname(String name)
    {
        this.ename=name;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime =endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
