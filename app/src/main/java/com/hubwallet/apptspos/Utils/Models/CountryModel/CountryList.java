package com.hubwallet.apptspos.Utils.Models.CountryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryList {
    @SerializedName("result")
    @Expose
    private List<CountryData> result = null;

    public List<CountryData> getResult() {
        return result;
    }

    public void setResult(List<CountryData> result) {
        this.result = result;
    }

}