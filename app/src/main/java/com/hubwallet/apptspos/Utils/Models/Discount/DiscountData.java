package com.hubwallet.apptspos.Utils.Models.Discount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountData {
    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("discount_for")
    @Expose
    private String discountFor;
    @SerializedName("coupon_type")
    @Expose
    private String couponType;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("min_amount")
    @Expose
    private String minAmount;
    @SerializedName("is_active")
    @Expose
    private String isActive;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getDiscountFor() {
        return discountFor;
    }

    public void setDiscountFor(String discountFor) {
        this.discountFor = discountFor;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
