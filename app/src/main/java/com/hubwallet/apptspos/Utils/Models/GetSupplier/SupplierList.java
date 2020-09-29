package com.hubwallet.apptspos.Utils.Models.GetSupplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupplierList {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private List<SupplierData> message = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SupplierData> getMessage() {
        return message;
    }

    public void setMessage(List<SupplierData> message) {
        this.message = message;
    }

}
