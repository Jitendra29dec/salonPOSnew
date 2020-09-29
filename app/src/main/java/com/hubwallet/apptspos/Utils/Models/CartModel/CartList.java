package com.hubwallet.apptspos.Utils.Models.CartModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartList {

    @SerializedName("result")
    @Expose
    private List<CartData> result = null;


    public List<CartData> getResult() {
        return result;
    }

    public void setResult(List<CartData> result) {
        this.result = result;
    }
}