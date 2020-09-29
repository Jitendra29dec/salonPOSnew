package com.hubwallet.apptspos.Utils.Models.GetServicesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetServicesList {

    @SerializedName("result")
    @Expose
    private List<GetServicesData> result = null;

    public List<GetServicesData> getResult() {
        return result;
    }

    public void setResult(List<GetServicesData> result) {
        this.result = result;
    }

}
