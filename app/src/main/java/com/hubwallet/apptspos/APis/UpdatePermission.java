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

public class UpdatePermission {
    private  String stylistId;
    private String permissionId;
    private String vendorID;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.updatePermission, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("update_permission", jsonObject.getString("message"));
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
            param.put("stylist_id", stylistId);
            param.put("permission_id",permissionId);
            return param;
        }
    };

    public UpdatePermission(String vendorID, String stylistId,String permissionId, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.stylistId = stylistId;
        this.permissionId = permissionId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
