package com.myapplication;

import android.os.ParcelUuid;

import com.google.firebase.database.core.view.Event;

public class contact {

    String EventType , EventName ,Location;
    public contact(String EventType , String EventName, String location)
    {
        this.EventType = EventType;
        this.EventName = EventName;
        this.Location = location;


    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
