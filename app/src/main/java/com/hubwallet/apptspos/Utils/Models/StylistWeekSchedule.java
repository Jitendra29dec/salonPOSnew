package com.hubwallet.apptspos.Utils.Models;

public class StylistWeekSchedule {
    String day;
    String from;
    String to;
    boolean onOrOff = false;

    public StylistWeekSchedule(String day, String from, String to, boolean onOrOff) {
        this.day = day;
        this.from = from;
        this.to = to;
        this.onOrOff = onOrOff;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean isOn() {
        return onOrOff;
    }

    public void setOnOrOff(boolean onOrOff) {
        this.onOrOff = onOrOff;
    }
}
