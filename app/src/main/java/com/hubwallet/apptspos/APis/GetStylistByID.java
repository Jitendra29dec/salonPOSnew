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

public class GetStylistByID {
    private final String stylistId;
    private final ApiCommunicator apiCommunicator;
    private String vendorId;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.getStylistById, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("getStylistById ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("get_stylist_by_id", response);
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
            param.put("stylist_id", stylistId);
            param.put("vendor_id", vendorId);
            Log.e("getParams: ", stylistId);
            return param;
        }
    };

    public GetStylistByID(String stylistId, String vendorId, ApiCommunicator apiCommunicator) {

        this.stylistId = stylistId;
        this.vendorId = vendorId;
        this.apiCommunicator = apiCommunicator;

    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
