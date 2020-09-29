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

public class GetNotes {
    private final String appointmentId;
    private String vendorId;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.getAppointmenrNotes, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject res = new JSONObject(response);
                if (res.getString("status").equalsIgnoreCase("1")) {
                    JSONObject result = new JSONObject(res.getString("result"));
                    apiCommunicator.getApiData("get_notes", result.getString("note"));
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
            param.put("vendor_id", vendorId);
            param.put("appointment_id", appointmentId);
            return param;
        }
    };

    public GetNotes(String vendorId, String appointmentId, ApiCommunicator apiCommunicator) {
        this.vendorId = vendorId;
        this.appointmentId = appointmentId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
