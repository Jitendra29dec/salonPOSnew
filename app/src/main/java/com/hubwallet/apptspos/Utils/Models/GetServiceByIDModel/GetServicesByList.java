package com.hubwallet.apptspos.Utils.Models.GetServiceByIDModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetServicesByList {

    @SerializedName("result")
    @Expose
    private List<GetServicesByData> result = null;

    public List<GetServicesByData> getResult() {
        return result;
    }

    public void setResult(List<GetServicesByData> result) {
        this.result = result;
    }

}
