package com.hubwallet.apptspos.Utils.Models.GetProductByIdModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductByIdM {

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
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("purchase_price")
    @Expose
    private String purchasePrice;
    @SerializedName("price_wholesale")
    @Expose
    private String priceWholesale;
    @SerializedName("low_qty_warning")
    @Expose
    private String lowQtyWarning;
    @SerializedName("business_use")
    @Expose
    private String businessUse;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("commission_percent")
    @Expose
    private String commission_percent;

    @SerializedName("commission_amount")
    @Expose
    private String commission_amount;
    @SerializedName("is_tax")
    @Expose
    private String is_tax;
    @SerializedName("supplier_id")
    @Expose
    private String supplier_id;


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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPriceWholesale() {
        return priceWholesale;
    }

    public void setPriceWholesale(String priceWholesale) {
        this.priceWholesale = priceWholesale;
    }

    public String getLowQtyWarning() {
        return lowQtyWarning;
    }

    public void setLowQtyWarning(String lowQtyWarning) {
        this.lowQtyWarning = lowQtyWarning;
    }

    public String getBusinessUse() {
        return businessUse;
    }

    public void setBusinessUse(String businessUse) {
        this.businessUse = businessUse;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(String commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getCommission_percent() {
        return commission_percent;
    }

    public void setCommission_percent(String commission_percent) {
        this.commission_percent = commission_percent;
    }

    public String getIs_tax() {
        return is_tax;
    }

    public void setIs_tax(String is_tax) {
        this.is_tax = is_tax;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }
}