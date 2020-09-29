package com.hubwallet.apptspos.APis;

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

public class AppointmentsServices {
    private final String appointmentId;
    private final ApiCommunicator apiCommunicator;
    private String vendorId;
    private StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.servicesOfAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("appointment_service", response);
                } else {
                    apiCommunicator.getApiData("appointment_service", jsonObject.getString("message"));
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
            param.put("appointment_id", appointmentId);
            param.put("vendor_id", vendorId);
            return param;
        }
    };

    public AppointmentsServices(String appointmentId, String vendorId, ApiCommunicator apiCommunicator) {

        this.appointmentId = appointmentId;
        this.vendorId = vendorId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
