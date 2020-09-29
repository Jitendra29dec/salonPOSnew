package com.hubwallet.apptspos.Utils.Models.ProductCategoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductCategory {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<ProductCategoryData> result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductCategoryData> getResult() {
        return result;
    }

    public void setResult(List<ProductCategoryData> result) {
        this.result = result;
    }

   /* @SerializedName("result")
    @Expose
    private List<ProductCategoryData> result = null;

    public List<ProductCategoryData> getResult() {
        return result;
    }

    public void setResult(List<ProductCategoryData> result) {
        this.result = result;
    }*/

}