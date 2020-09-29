package com.hubwallet.apptspos.Utils.Models;

public class GroupAppointmentModel {

    private String time;
    private String date;
    private String stylist;
    private String service;
    private String price;
    private String duration;
    private String appointmentId;
    private String time24;
    private String
            Note;

    public GroupAppointmentModel(String date, String time, String service, String stylist, String price, String duration) {

        this.date = date;
        this.time = time;
        this.service = service;
        this.stylist = stylist;
        this.price = price;
        this.duration = duration;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTime24() {
        return time24;
    }

    public void setTime24(String time24) {
        this.time24 = time24;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStylist() {
        return stylist;
    }

    public void setStylist(String stylist) {
        this.stylist = stylist;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
