package com.hubwallet.apptspos.Utils.Models.CountryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryData {


    @SerializedName("country_d")
    @Expose
    private String countryD;
    @SerializedName("country_name")
    @Expose
    private String countryName;

    public String getCountryD() {
        return countryD;
    }

    public void setCountryD(String countryD) {
        this.countryD = countryD;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
