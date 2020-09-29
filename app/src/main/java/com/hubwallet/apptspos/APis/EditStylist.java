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

public class EditStylist {
    private final String vendorId;
    private final String stylistTypr;
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String phone;
    private final String alternatePhone;
    private final String countryId;
    private final String stateId;
    private final String postalCode;
    private final String address;
    private final String services;
    private final String days;
    private String photo;
    private String city;
    private String email;
    private String stylistId;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.editStylist, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("onResponseEdit: ", response);
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString("status");
                if (status.equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("stylist_edited", object.getString("message"));
                } else {
                    apiCommunicator.getApiData("stylist_edited", object.getString("message"));

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
            param.put("photo", photo);
            param.put("firstname", firstName);
            param.put("lastname", lastName);
            param.put("nickname", nickName);
            //    param.put("email", email);
            param.put("alternate_phone", alternatePhone);
            param.put("stylist_type", stylistTypr);
            param.put("country_id", countryId);
            param.put("state_id", stateId);
            param.put("city", city);
            param.put("postal_code", postalCode);
            param.put("address", address);
            param.put("services", services);
            param.put("days", days);
            param.put("phone", phone);
            param.put("stylist_id", stylistId);
            Log.e("getParams: ", stylistId);
            return param;
        }
    };


    public EditStylist(String vendorId, String photo, String stylistTypr, String firstName, String lastName, String nickName, String phone, String alternatePhone, String countryId, String stateId, String postalCode, String address, String city, String services, String days, String email, String stylistId, ApiCommunicator apiCommunicator) {
        this.vendorId = vendorId;
        this.photo = photo;
        this.stylistTypr = stylistTypr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.phone = phone;
        this.alternatePhone = alternatePhone;
        this.countryId = countryId;
        this.stateId = stateId;
        this.postalCode = postalCode;
        this.address = address;
        this.city = city;
        this.services = services;
        this.days = days;
        this.email = email;
        this.stylistId = stylistId;
        this.apiCommunicator = apiCommunicator;

    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
