package com.hubwallet.apptspos.APis;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class ShowDetailAppointment {

    private final String vendorId;
    private final String appointmentId;
    private ApiCommunicator apiCommunicator;
    private StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.detailAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponse: ", response);
            apiCommunicator.getApiData("detail_appointment", response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> param = new HashMap<>();
            param.put("appointment_id", appointmentId);
            param.put("vendor_id", vendorId);
            return param;
        }
    };

    public ShowDetailAppointment(String vendorId, String appointmentId, ApiCommunicator apiCommunicator) {
        this.vendorId = vendorId;
        this.appointmentId = appointmentId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }

}
