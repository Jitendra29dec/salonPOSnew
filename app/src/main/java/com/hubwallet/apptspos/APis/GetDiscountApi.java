package com.hubwallet.apptspos.APis;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import java.util.HashMap;
import java.util.Map;


public class GetDiscountApi {
    private ApiCommunicator apiCommunicator;
    private String vendorId;
    StringRequest request = new StringRequest(Request.Method.POST, Constants.getDiscounts, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            apiCommunicator.getApiData("discount_response", response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> param = new HashMap<>();
            param.put("vendor_id", vendorId);
            return param;
        }
    };

    public GetDiscountApi(ApiCommunicator apiCommunicator, String vendorId) {
        this.apiCommunicator = apiCommunicator;
        this.vendorId = vendorId;
    }

    public StringRequest getRequest() {
        return request;
    }
}
