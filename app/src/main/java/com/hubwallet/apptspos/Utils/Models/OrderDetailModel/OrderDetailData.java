package com.hubwallet.apptspos.Utils.Models.OrderDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailData {

    @SerializedName("editData")
    @Expose
    private EditData editData;
    @SerializedName("customerInfo")
    @Expose
    private CustomerInfo customerInfo;
    @SerializedName("appointmentData")
    @Expose
    private List<AppointmentData> appointmentData;
    @SerializedName("paymentInfo")
    @Expose
    private PaymentInfo paymentInfo;
    @SerializedName("taxInfo")
    @Expose
    private List<TaxInfo> taxInfo = null;
    @SerializedName("orderInfo")
    @Expose
    private List<OrderInfo> orderInfo = null;
    @SerializedName("productInfo")
    @Expose
    private List<ProductInfo> productInfo = null;
    @SerializedName("orderHistoryInfo")
    @Expose
    private List<OrderHistoryInfo> orderHistoryInfo = null;

    public EditData getEditData() {
        return editData;
    }

    public void setEditData(EditData editData) {
        this.editData = editData;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

 /*   public AppointmentData getAppointmentData() {
        return appointmentData;
    }

    public void setAppointmentData(AppointmentData appointmentData) {
        this.appointmentData = appointmentData;
    }*/

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public List<TaxInfo> getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(List<TaxInfo> taxInfo) {
        this.taxInfo = taxInfo;
    }

    public List<OrderInfo> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(List<OrderInfo> orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }

    public List<OrderHistoryInfo> getOrderHistoryInfo() {
        return orderHistoryInfo;
    }

    public void setOrderHistoryInfo(List<OrderHistoryInfo> orderHistoryInfo) {
        this.orderHistoryInfo = orderHistoryInfo;
    }


    public List<AppointmentData> getAppointmentData() {
        return appointmentData;
    }
}
