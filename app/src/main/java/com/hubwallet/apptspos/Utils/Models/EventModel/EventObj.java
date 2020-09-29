package com.hubwallet.apptspos.Utils.Models.EventModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventObj {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
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
    @SerializedName("color_id")
    @Expose
    private String colorId;

    @SerializedName(("appointment_type"))
    @Expose
    private String appointmentType;
    @SerializedName(("token_no"))
    @Expose
    private String tokenNumber;

    public String getTokenNumber() {
        return tokenNumber;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

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

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }


}