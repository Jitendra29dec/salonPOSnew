package com.hubwallet.apptspos.APis;

import com.android.volley.Request;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;
import com.hubwallet.apptspos.Utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCustomerPopup {

    private String vendorId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country = "";
    private String state = "";
    private String gender = "";
    private String preferredContact = "";
    private String homePhone = "";
    private String workPhone = "";
    private String ccEmail = "";
    private String referredBy = "";
    private String smsAlert = "";
    private String emailAlert = "";
    private ApiCommunicator apiCommunicator;

    StringRequest stringRequest = new StringRequest(Request.Method.POST,
            Constants.addCustomer,
            response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        apiCommunicator.getApiData("customer_added", jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtils.printLog("Add Customer Exc", ":" + e.getMessage());
                    apiCommunicator.getApiData("customer_added", "error");
                }
            }, error -> {
        LogUtils.printLog("Add Customer Error", ":" + error.getMessage());
        apiCommunicator.getApiData("customer_added", "error");
    }) {
        @Override
        protected Map<String, String> getParams() {
            HashMap<String, String> param = new HashMap<>();
            //vendor_id, firstname, lastname, email, cc_email,
            // gender, refered_by, country, state, city, mobile_phone,
            // home_phone, work_phone, preferred_contact, sms_alert,
            // email_alert, address
            param.put("vendor_id", vendorId);
            param.put("firstname", firstname);
            param.put("lastname", lastname);
            param.put("email", email);
            param.put("cc_email", ccEmail);
            param.put("gender", gender);
            param.put("refered_by", referredBy);
            param.put("country", country);
            param.put("state", state);
            param.put("city", city);
            param.put("mobile_phone", phone);
            param.put("home_phone", homePhone);
            param.put("work_phone", workPhone);
            param.put("preferred_contact", preferredContact);
            param.put("sms_alert", smsAlert);
            param.put("email_alert", emailAlert);
            param.put("address", address);
            return param;
        }
    };

    public AddCustomerPopup(String vendorId, String firstname, String lastname, String email,
                            String phone, String homePhone, String workPhone,
                            String address, String city, String state, String country,
                            String gender, String preferredContact, String ccEmail,
                            String referredBy, String smsAlert, String emailAlert,
                            ApiCommunicator apiCommunicator) {
        this.vendorId = vendorId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.ccEmail = ccEmail;
        this.phone = phone;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.gender = gender;
        this.preferredContact = preferredContact;
        this.referredBy = referredBy;
        this.smsAlert = smsAlert;
        this.emailAlert = emailAlert;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
