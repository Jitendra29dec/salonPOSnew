package com.hubwallet.apptspos.Utils.Models.GetCustomerById;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCustomerBy {

    @SerializedName("result")
    @Expose
    private List<GetCustomerByIdData> result = null;

    public List<GetCustomerByIdData> getResult() {
        return result;
    }

    public void setResult(List<GetCustomerByIdData> result) {
        this.result = result;
    }

}
