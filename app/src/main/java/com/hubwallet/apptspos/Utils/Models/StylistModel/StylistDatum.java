package com.hubwallet.apptspos.Utils.Models.StylistModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StylistDatum {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("stylist_id")
    @Expose
    private String stylistId;
    @SerializedName("stylist_name")
    @Expose
    private String stylistName;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("duration")
    @Expose
    private double duration;

    public double getDuration() {
        return duration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return stylistName;
    }
}