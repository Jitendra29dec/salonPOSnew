package com.hubwallet.apptspos.APis;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GroupAppointmentDetailApi {
    private String tokenNumber;
    private ApiCommunicator apiCommunicator;
    private String vendorId;
    private StringRequest stringRequest = new StringRequest(1, Constants.getGroupAppointmentsDetails, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("group_detail", response);
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
            param.put("vendor_id", vendorId);
            param.put("token_no", tokenNumber);
            return param;


        }
    };

    public GroupAppointmentDetailApi(String tokenNumber, String vendorId, ApiCommunicator apiCommunicator) {
        this.tokenNumber = tokenNumber;
        this.vendorId = vendorId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }

}
