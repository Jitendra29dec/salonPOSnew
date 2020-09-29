package com.hubwallet.apptspos.Utils.Models;

public class ServiceStylist {
    private String stylistName;
    private String serviceName;
    private String serviceId;
    private String stylistId;

    public ServiceStylist() {
    }

    public ServiceStylist(String stylistName, String serviceName, String serviceId, String stylistId) {
        this.stylistName = stylistName;
        this.serviceName = serviceName;
        this.serviceId = serviceId;
        this.stylistId = stylistId;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }
}
