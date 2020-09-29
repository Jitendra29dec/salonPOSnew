package com.hubwallet.apptspos.Utils.Models.GetStylistModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStylistList {

    @SerializedName("result")
    @Expose
    private List<GetStylistData> result = null;

    public List<GetStylistData> getResult() {
        return result;
    }

    public void setResult(List<GetStylistData> result) {
        this.result = result;
    }

}