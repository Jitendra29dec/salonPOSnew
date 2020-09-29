package com.hubwallet.apptspos.Utils.Models.OrderDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentData {

    @SerializedName("appointment_id")
    @Expose
    private String appointmentId;

    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("appointment_date")
    @Expose
    private String apppointmentDate;


    @SerializedName("stylist_name")
    @Expose
    private String stylistName;


    public String getAppointmentId() {
        return appointmentId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDuration() {
        return duration;
    }

    public String getApppointmentDate() {
        return apppointmentDate;
    }

    public String getStylistName() {
        return stylistName;
    }

    public String getPrice() {
        return price;
    }
}