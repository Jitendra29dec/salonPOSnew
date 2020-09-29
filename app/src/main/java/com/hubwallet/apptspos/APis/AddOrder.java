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

public class AddOrder {
    private final String customerID;
    private final String vendorId;
    private final String appointmentID;
    private final String paymentTupe;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.addOrder, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponse: ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("add_order", response);
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
            param.put("customer_id", customerID);
            param.put("payment_type", paymentTupe);
            param.put("appointment_id", appointmentID);
            return param;
        }
    };

    public AddOrder(String customerID, String vendorId, String appointmentID, String paymentType, ApiCommunicator apiCommunicator) {
        this.customerID = customerID;
        this.vendorId = vendorId;
        this.appointmentID = appointmentID;
        this.paymentTupe = paymentType;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
