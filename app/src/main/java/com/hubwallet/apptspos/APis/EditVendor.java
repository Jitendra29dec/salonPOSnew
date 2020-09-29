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

public class EditVendor {
    private  String name;
    private String supplierId;
    private  String email;
    private  String phone;
    private  String stateId;
    private  String city;
    private  String zipcode;
    private  String address;
    private String vendorID;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.editVendor, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("vendor_edited", jsonObject.getString("message"));
                    Log.e("onResponse: ", "called");
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
            param.put("supplier_id",supplierId);
            param.put("name", name);
            param.put("email", email);
            param.put("phone", phone);
            param.put("state_id", stateId);
            param.put("city", city);
            param.put("zipcode", zipcode);
            param.put("address", address);
            return param;
        }
    };

    public EditVendor(String vendorID, String supplierId, String name, String email ,String phone ,String stateId ,String city ,String zipcode ,String address, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.stateId = stateId;
        this.supplierId = supplierId;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
