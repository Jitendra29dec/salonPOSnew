package com.hubwallet.apptspos.Utils.Models.GetServiceByIDModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetServicesByData {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("sku")
    @Expose
    private Object sku;
    @SerializedName("service_price")
    @Expose
    private String servicePrice;
    @SerializedName("service_duration")
    @Expose
    private String serviceDuration;

    @SerializedName("commission_percent")
    @Expose
    private String commission_percent;

    @SerializedName("commission_amount")
    @Expose
    private String commission_amount;

    @SerializedName("tax_id")
    @Expose
    private String tax_id;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getSku() {
        return sku;
    }

    public void setSku(Object sku) {
        this.sku = sku;
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

    public String getCommission_percent() {
        return commission_percent;
    }

    public void setCommission_percent(String commission_percent) {
        this.commission_percent = commission_percent;
    }

    public String getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(String commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }
}
