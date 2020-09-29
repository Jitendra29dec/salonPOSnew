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

public class DeleteAppointment {
    private final String vendorID;
    private final String appointmentId;
    private final ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.deleteAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponse: ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("appointment_deleted", jsonObject.getString("message"));
                } else {
                    apiCommunicator.getApiData("appointment_deleted", jsonObject.getString("message"));
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
            param.put("vendor_id", vendorID);
            param.put("appointment_id", appointmentId);

            return param;

        }
    };

    public DeleteAppointment(String vendorID, String appointmentId, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.appointmentId = appointmentId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }


}
