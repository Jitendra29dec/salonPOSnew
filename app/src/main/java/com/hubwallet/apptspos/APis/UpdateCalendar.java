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

public class UpdateCalendar {
    private  String days;
    private String vendorID;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UpdateCalendar, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("update_calendar", jsonObject.getString("message"));
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
            param.put("vendor_id", vendorID);
            param.put("days", days);
            return param;
        }
    };

    public UpdateCalendar(String vendorID, String days, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.days = days;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
