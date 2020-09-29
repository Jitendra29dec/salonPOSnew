package com.hubwallet.apptspos.Utils.Models.GetSupplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierData {
    @SerializedName("supplier_id")
    @Expose
    private String supplierId;
    @SerializedName("supplier_name")
    @Expose
    private String supplierName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
