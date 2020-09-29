package com.hubwallet.apptspos.Utils.Models.BrandModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBrand {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private List<BrandData> message = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BrandData> getMessage() {
        return message;
    }

    public void setMessage(List<BrandData> message) {
        this.message = message;
    }

  /*  @SerializedName("message")
    @Expose
    private List<BrandData> message = null;

    public List<BrandData> getList() {
        return message;
    }

    public void setMessage(List<BrandData> message) {
        this.message = message;
    }*/

}