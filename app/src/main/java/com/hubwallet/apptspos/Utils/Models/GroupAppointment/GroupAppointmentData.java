package com.hubwallet.apptspos.Utils.Models.GroupAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupAppointmentData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("rendering")
    @Expose
    private String rendering;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("backgroundColor")
    @Expose
    private String backgroundColor;
    @SerializedName("stylist_id")
    @Expose
    private String stylistId;
    @SerializedName("stylist_name")
    @Expose
    private String stylistName;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("note")
    @Expose
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRendering() {
        return rendering;
    }

    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
