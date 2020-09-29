package com.hubwallet.apptspos.Utils.Models.GetStateModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStateList {

    @SerializedName("result")
    @Expose
    private List<GetStateData> result = null;


    public List<GetStateData> getResult() {
        return result;
    }

    public void setResult(List<GetStateData> result) {
        this.result = result;
    }

}