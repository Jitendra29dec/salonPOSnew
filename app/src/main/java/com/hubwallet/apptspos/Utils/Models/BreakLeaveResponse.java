package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreakLeaveResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private BreakLeaveData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BreakLeaveData getData() {
        return data;
    }

    public void setData(BreakLeaveData data) {
        this.data = data;
    }

}
