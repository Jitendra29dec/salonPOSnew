package com.hubwallet.apptspos.APis;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class AddToCart {
    private final String productId;
    private final String quantity;
    private final String appointmentID;
    private String cutomerID;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.addCart, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {


            apiCommunicator.getApiData("add_cart", response);

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            HashMap<String, String> param = new HashMap<>();
            param.put("appointment_id", appointmentID);
            param.put("product_id", productId);
            param.put("quantity", quantity);
            param.put("customer_id", cutomerID);
            return param;
        }
    };

    public AddToCart(String productId, String quantity, String appointmentID, String cutomerID, ApiCommunicator apiCommunicator) {
        this.productId = productId;
        this.quantity = quantity;
        this.appointmentID = appointmentID;
        this.cutomerID = cutomerID;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
