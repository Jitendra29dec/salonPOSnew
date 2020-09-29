package com.hubwallet.apptspos.Utils.Models.CheckoutServicesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckoutServices {
    @SerializedName("result")
    @Expose
    private List<CheckoutServiceData> result = null;

    public List<CheckoutServiceData> getResult() {
        return result;
    }

    public void setResult(List<CheckoutServiceData> result) {
        this.result = result;
    }

}
