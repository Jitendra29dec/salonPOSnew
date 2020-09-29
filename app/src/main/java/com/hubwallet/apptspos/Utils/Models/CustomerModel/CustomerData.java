package com.hubwallet.apptspos.Utils.Models.CustomerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerData {

    @SerializedName("customer_data")
    @Expose
    private List<CustomerDatum> customerData = null;

    public List<CustomerDatum> getCustomerData() {
        return customerData;
    }

    public void setCustomerData(List<CustomerDatum> customerData) {
        this.customerData = customerData;
    }

}
