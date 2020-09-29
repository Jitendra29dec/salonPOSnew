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


public class UpdateNotes {
    private final String vendorId;
    private final String appointmentId;
    private final String note;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.setAppointmenrNotes, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponse: ", response);
            try {
                JSONObject object = new JSONObject(response);
                if (object.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("update_note", object.getString("message"));

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
            param.put("note", note);
            return param;
        }
    };

    public UpdateNotes(String vendorId, String appointmentId, String note, ApiCommunicator apiCommunicator) {
        this.vendorId = vendorId;
        this.appointmentId = appointmentId;
        this.note = note;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
