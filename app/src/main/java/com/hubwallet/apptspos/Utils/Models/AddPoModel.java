package com.hubwallet.apptspos.Utils.Models;

import java.util.ArrayList;
import java.util.List;

public class AddPoModel {
    private String productId;
   // private List<String> product;
    private String quantity;
    private String rate;
    private String tax;
    private  String amount;

  /*  public List<String> getProduct() {
        return product==null?new ArrayList<>():product;
    }

    public void setProduct(List<String> product) {
        this.product = product;
    }
*/
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity==null?"0":quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate==null?"0":rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTax() {
        return tax==null?"0":tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getAmount() {
        return amount==null?"0":amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
