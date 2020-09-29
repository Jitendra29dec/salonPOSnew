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

public class EditCustomer {
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String phone;
    private final String address;
    private final String picode;
    private final String city;
    private final String notes;
    private final String birthday;
    private final String vendorId;
    private String customerID;
    private String photo;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.editCustomer, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("customer_edited", jsonObject.getString("message"));
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
            param.put("firstname", firstname);
            param.put("lastname", lastname);
            param.put("email", email);
            param.put("phone", phone);
            param.put("vendor_id", vendorId);
            param.put("address", address);
            param.put("birthday", birthday);
            param.put("pincode", picode);
            param.put("city", city);
            param.put("notes", notes);
            param.put("customer_id", customerID);
            param.put("photo", photo);
            return param;
        }
    };

    public EditCustomer(String customerID, String firstname, String lastname, String email, String phone, String address, String picode, String city, String notes, String birthday, String vendorId, String photo, ApiCommunicator apiCommunicator) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.picode = picode;
        this.city = city;
        this.notes = notes;
        this.birthday = birthday;
        this.vendorId = vendorId;
        this.photo = photo;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
