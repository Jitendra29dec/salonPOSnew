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

public class AppointmentStatus {

    private final String appointmentId;
    private final String status;
    private ApiCommunicator apiCommunicator;
    private StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.statusAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Log.e("onStatud ", response);
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    if (status.equalsIgnoreCase("checkout")) {
                        apiCommunicator.getApiData("appointment_status_checkout", jsonObject.getString("message"));

                    }
                }
                apiCommunicator.getApiData("appointment_status", jsonObject.getString("message"));
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
            param.put("status", status);
            return param;
        }
    };

    public AppointmentStatus(String appointmentId, String status, ApiCommunicator apiCommunicator) {

        this.appointmentId = appointmentId;
        this.status = status;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
