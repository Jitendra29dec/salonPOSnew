package com.hubwallet.apptspos.APis;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowAppointment {
    private String selectedStylist = "";
    private String vendorID;
    private ApiCommunicator apiCommunicator;
    private StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.showAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponse: ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("event_list", response);
                } else {
                    jsonObject.getString("message");
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
        protected Map<String, String> getParams() {
            HashMap<String, String> param = new HashMap<>();
            param.put("vendor_id", vendorID);
            if (!selectedStylist.isEmpty()) {
                param.put("stylist_id", selectedStylist);
            }
            return param;
        }
    };

    public ShowAppointment(String vendorID, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.apiCommunicator = apiCommunicator;

    }

    public ShowAppointment(String vendorId, ApiCommunicator apiCommunicator, String selectedStylist) {
        this.selectedStylist = selectedStylist;
        this.vendorID = vendorId;
        this.apiCommunicator = apiCommunicator;

    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
