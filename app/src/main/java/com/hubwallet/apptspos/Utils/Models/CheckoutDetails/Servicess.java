package com.hubwallet.apptspos.Utils.Models.CheckoutDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Servicess {

    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_price")
    @Expose
    private String servicePrice;
    @SerializedName("service_duration")
    @Expose
    private String serviceDuration;
    @SerializedName("stylist_id")
    @Expose
    private String stylistId;
    @SerializedName("stylist_price")
    @Expose
    private String stylistPrice;
    @SerializedName("stylist_duration")
    @Expose
    private String stylistDuration;
    @SerializedName("stylist_name")
    @Expose
    private String stylistName;


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(String serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getStylistPrice() {
        return stylistPrice;
    }

    public void setStylistPrice(String stylistPrice) {
        this.stylistPrice = stylistPrice;
    }

    public String getStylistDuration() {
        return stylistDuration;
    }

    public void setStylistDuration(String stylistDuration) {
        this.stylistDuration = stylistDuration;
    }

    public String getStylistName() {
        return stylistName;
    }
}