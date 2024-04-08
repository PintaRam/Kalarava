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

    public String getTime1() {
        return startTime;
    }

    public void setTime1(String startTime) {
        this.startTime = startTime;
    }

    public String getDate1() {
        return startDate;
    }

    public void setDate1(String startDate) {
        this.startDate = startDate;
    }

    public String getTime() {
        return endTime;
    }

    public void setTime(String endTime) {
        this.endTime =endTime;
    }

    public String getDate() {
        return endDate;
    }

    public void setDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
