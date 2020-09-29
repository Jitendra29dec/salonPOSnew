package com.hubwallet.apptspos.Utils.Models.CheckoutDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckoutData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service")
    @Expose
    private List<Servicess> service = null;
    @SerializedName("cart")
    @Expose
    private List<Cart> cart = null;
    @SerializedName("service_total")
    @Expose
    private String serviceTotal;
    @SerializedName("cart_total")
    @Expose
    private String cartTotal;
    @SerializedName("final_total")
    @Expose
    private String totalPrice;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Servicess> getService() {
        return service;
    }

    public void setService(List<Servicess> service) {
        this.service = service;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public String getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(String serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public String getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(String cartTotal) {
        this.cartTotal = cartTotal;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}