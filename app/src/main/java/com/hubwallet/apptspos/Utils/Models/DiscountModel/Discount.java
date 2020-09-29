package com.hubwallet.apptspos.Utils.Models.DiscountModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Discount {


    @SerializedName("serviceDiscount")
    @Expose
    private List<ServiceDiscount> serviceDiscount = null;
    @SerializedName("productDiscount")
    @Expose
    private List<ProductDiscount> productDiscount = null;

    public List<ServiceDiscount> getServiceDiscount() {
        return serviceDiscount;
    }

    public void setServiceDiscount(List<ServiceDiscount> serviceDiscount) {
        this.serviceDiscount = serviceDiscount;
    }

    public List<ProductDiscount> getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(List<ProductDiscount> productDiscount) {
        this.productDiscount = productDiscount;
    }
}
