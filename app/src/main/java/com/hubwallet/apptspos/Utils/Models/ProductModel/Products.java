package com.hubwallet.apptspos.Utils.Models.ProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {

    @SerializedName("result")
    @Expose
    private List<ProductData> result = null;

    public List<ProductData> getResult() {
        return result;
    }

    public void setResult(List<ProductData> result) {
        this.result = result;
    }

}