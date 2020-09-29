package com.hubwallet.apptspos.Utils.Models.OrderDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("result")
    @Expose
    private OrderDetailData result;

    public OrderDetailData getResult() {
        return result;
    }

    public void setResult(OrderDetailData result) {
        this.result = result;
    }

}
