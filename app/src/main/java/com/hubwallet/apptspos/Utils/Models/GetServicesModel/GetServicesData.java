package com.hubwallet.apptspos.Utils.Models.GetServicesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetServicesData {

    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("service_id")
    @Expose
    private String serviceID;

    private boolean isChecked = false;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
