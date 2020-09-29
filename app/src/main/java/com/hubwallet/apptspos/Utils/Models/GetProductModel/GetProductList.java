package com.hubwallet.apptspos.Utils.Models.GetProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductList {

    @SerializedName("result")
    @Expose
    private List<GetProductData> result = null;

    public List<GetProductData> getResult() {
        return result;
    }

    public void setResult(List<GetProductData> result) {
        this.result = result;
    }

}