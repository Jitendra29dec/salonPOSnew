package com.hubwallet.apptspos.APis;

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

public class DeleteStylist {
    private String vendorID;
    private String stylistId;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.deleteStylist, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //   Log.e( "onResponse: ",response );
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("delete_stylist", jsonObject.getString("message"));
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
            param.put("stylist_id", stylistId);
            param.put("vendor_id", vendorID);
            return param;
        }
    };

    public DeleteStylist(String vendorID, String stylistId, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;

        this.stylistId = stylistId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
