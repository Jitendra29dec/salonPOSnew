package com.hubwallet.apptspos.Utils.Models.GetOrderList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderList {

    @SerializedName("result")
    @Expose
    private List<OrderData> result = null;

    public List<OrderData> getResult() {
        return result;
    }

    public void setResult(List<OrderData> result) {
        this.result = result;
    }

}
