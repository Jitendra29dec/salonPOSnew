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

public class GetStylists {
    private ApiCommunicator apiCommunicator;
    private String vendorId;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.getStylist, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponse: ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (
                        jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("get_stylists", response);
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
            return param;
        }
    };

    public GetStylists(ApiCommunicator apiCommunicator, String vendorId) {
        this.apiCommunicator = apiCommunicator;
        this.vendorId = vendorId;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
