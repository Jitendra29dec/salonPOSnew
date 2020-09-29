package com.hubwallet.apptspos.APis;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProduct {
    private final String vendorId;
    private final String barcodeID;
    private final String brandId;
    private final String categoryId;
    private final String productName;
    private final String sku;
    private final String descripyion;
    private final String retailPrice;
    private final String wholesalePrice;
    private final String photo;
    private final String businessUse;
    private ApiCommunicator apiCommunicator;
    private String lowQtyyWarning;
    private String purchasePrice;
    private String productId;
    private String quantity;
    private String commission_percent;
    private String commission_amount;
    private String isTax;
    private String tax;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.editProduct, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponse: ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("product_edited", jsonObject.getString("message"));
                } else {
                    apiCommunicator.getApiData("product_added_failed", jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> param = new HashMap<>();
            param.put("vendor_id", vendorId);
            param.put("barcode_id", barcodeID);
            param.put("brand_id", brandId);
            param.put("category_id", categoryId);
            param.put("product_name", productName);
            param.put("sku", sku);
            param.put("description", descripyion);
            param.put("retail_price", retailPrice);
            param.put("purchase_price", purchasePrice);
            param.put("wholesale_price", wholesalePrice);
            param.put("low_qty_warning", lowQtyyWarning);
            param.put("photo", photo);
            param.put("business_use", businessUse);
            param.put("product_id", productId);
            param.put("quantity",quantity);
            param.put("commission_percent",commission_percent);
            param.put("commission_amount",commission_amount);
            param.put("tax_amount",isTax);
             param.put("tax",tax);
            return param;

        }
    };

    public EditProduct(ApiCommunicator apiCommunicator, String vendorId, String barcodeID, String brandId, String categoryId, String productName, String sku, String descripyion, String retailPrice, String wholesalePrice, String photo, String businessUse, String lowQtyyWarning, String purchasePrice, String productId,
                       String quantity,String commission_percent,String commission_amount,String isTax,String tax) {
        this.apiCommunicator = apiCommunicator;
        this.vendorId = vendorId;
        this.barcodeID = barcodeID;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.sku = sku;
        this.descripyion = descripyion;
        this.retailPrice = retailPrice;
        this.wholesalePrice = wholesalePrice;
        this.photo = photo;
        this.businessUse = businessUse;
        this.lowQtyyWarning = lowQtyyWarning;
        this.purchasePrice = purchasePrice;
        this.productId = productId;
        this.quantity = quantity;
        this.commission_percent = commission_percent;
        this.commission_amount = commission_amount;
        this.isTax = isTax;
        this.tax = tax;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }


}
