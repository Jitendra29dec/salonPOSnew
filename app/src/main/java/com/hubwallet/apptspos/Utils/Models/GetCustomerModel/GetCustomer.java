package com.hubwallet.apptspos.Utils.Models.GetCustomerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCustomer {

    @SerializedName("result")
    @Expose
    private List<GetCustomerData> result = null;

    public List<GetCustomerData> getResult() {
        return result;
    }

    public void setResult(List<GetCustomerData> result) {
        this.result = result;
    }
}