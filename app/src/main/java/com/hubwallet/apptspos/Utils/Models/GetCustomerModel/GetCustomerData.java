package com.hubwallet.apptspos.Utils.Models.GetCustomerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCustomerData {


    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("mobile_phone")
    @Expose
    private String mobilePhone;
    @SerializedName("photo")
    @Expose
    private String Photo;
    private boolean isSelected = false;
    private boolean isCaptain = false;//for group Appointment

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }
}
