package com.example.Sameera.hearts.model;

import android.support.annotation.NonNull;


public class Doctor implements Comparable {
    private String date;
    private String time;
    private String name;
    private String location;

    public Doctor() {
    }

    public Doctor(String date, String time, String name, String location) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.location = location;


    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
