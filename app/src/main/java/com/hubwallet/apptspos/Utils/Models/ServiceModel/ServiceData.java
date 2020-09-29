package com.hubwallet.apptspos.Utils.Models.ServiceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceData {

    @SerializedName("service_data")
    @Expose
    private List<ServiceDatum> serviceData = null;

    public List<ServiceDatum> getServiceData() {
        return serviceData;
    }

    public void setServiceData(List<ServiceDatum> serviceData) {
        this.serviceData = serviceData;
    }

}
