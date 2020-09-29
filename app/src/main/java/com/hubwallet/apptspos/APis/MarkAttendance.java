package com.hubwallet.apptspos.APis;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class MarkAttendance {

    private String clock_as;
    private String vendorID;
    private ApiCommunicator apiCommunicator;
    private String pin;
    private StringRequest request = new StringRequest(Request.Method.POST, Constants.markAttendence, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("MarkAttendance: ", response);
            apiCommunicator.getApiData("pin_response", response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            HashMap<String, String> param = new HashMap<>();
            param.put("clock_as", clock_as);
            param.put("pin", pin);
            param.put("vendor_id", vendorID);
            return param;
        }
    };

    public MarkAttendance(String clock_as, String pin, String vendorID, ApiCommunicator apiCommunicator) {
        this.clock_as = clock_as;
        this.pin = pin;
        this.vendorID = vendorID;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getRequest() {
        return request;
    }
}
