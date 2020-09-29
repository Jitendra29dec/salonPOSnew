package com.hubwallet.apptspos.Utils.Models.StylistModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hubwallet.apptspos.base.BaseResponse;

import java.util.ArrayList;

public class StylistData extends BaseResponse {

    @SerializedName("result")
    @Expose
    private ArrayList<StylistDatum> stylistData = new ArrayList<StylistDatum>();

    public ArrayList<StylistDatum> getStylistData() {
        return stylistData;
    }

    public void setStylistData(ArrayList<StylistDatum> stylistData) {
        this.stylistData = stylistData;
    }

}