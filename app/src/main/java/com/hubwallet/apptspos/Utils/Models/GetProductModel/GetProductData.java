package com.hubwallet.apptspos.Utils.Models.GetProductModel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductData {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("barcode_id")
    @Expose
    private String barcodeId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price_retail")
    @Expose
    private String priceRetail;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("sku")
    @Expose
    private String sku;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(String priceRetail) {
        this.priceRetail = priceRetail;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @NonNull
    @Override
    public String toString() {
        return productName;
    }
}
