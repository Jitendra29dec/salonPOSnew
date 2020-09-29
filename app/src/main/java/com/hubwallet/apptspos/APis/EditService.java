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

public class EditService {
    private final String categoryId;
    private final String serviceName;
    private final String servicePrice;
    private final String serviceDuration;
    private String commissionAmount;
    private String commissionPercent;
    private String serviceId;
    private String vendorID;
    private String isTax;
    private String tax;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.editService, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("service_edited", jsonObject.getString("message"));
                    Log.e("onResponse: ", "called");
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
            HashMap<String, String> param = new HashMap<>();
            param.put("vendor_id",vendorID);
            param.put("category_id", categoryId);
            param.put("service_name", serviceName);
            param.put("service_duration", serviceDuration);
            param.put("commission_percent", commissionPercent);
            param.put("commission_amount",commissionAmount);
            param.put("service_id", serviceId);
            param.put("service_price", servicePrice);
            param.put("is_tax",isTax);
            return param;
        }
    };

    public EditService(String vendorID,String serviceId, String categoryId, String ServiceName, String servicePrice, String serviceDuration,String commissionAmount,String commissionPercent,String isTax,String tax ,ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.serviceId = serviceId;
        this.categoryId = categoryId;
        serviceName = ServiceName;
        this.servicePrice = servicePrice;
        this.serviceDuration = serviceDuration;
        this.commissionAmount = commissionAmount;
        this.commissionPercent =commissionPercent;
        this.isTax = isTax;
        this.tax = tax;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
