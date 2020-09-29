package com.hubwallet.apptspos.APis;

import com.android.volley.Request;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCustomer {
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
    private String photo;
    //    private String country = "";
//    private String state = "";
//    private String gender = "";
//    private String preferredContact = "";
//    private String homePhone = "";
//    private String workPhone = "";
//    private String appntEmail = "";
//    private String referredBy = "";
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
                }
            }, error -> {

    }) {
        @Override
        protected Map<String, String> getParams() {
            HashMap<String, String> param = new HashMap<>();
            //vendor_id, firstname, lastname, email, cc_email,
            // gender, refered_by, country, state, city, mobile_phone,
            // home_phone, work_phone, preferred_contact, sms_alert,
            // email_alert, address
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
            param.put("photo", photo);
            return param;
        }
    };

    public AddCustomer(String firstname, String lastname, String email, String phone, String address, String picode, String city, String notes, String birthday, String vendorId, String photo, ApiCommunicator apiCommunicator) {
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

//    public AddCustomer(String firstname, String lastname, String email,
//                       String phone, String address, String picode, String city,
//                       String notes, String birthday, String vendorId, String photo,
//                       String state, String country, String gender, String preferredContact,
//                       String homePhone, String workPhone, String appntEmail, String referredBy,
//                       String smsAlert, String emailAlert,
//                       ApiCommunicator apiCommunicator) {
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.picode = picode;
//        this.city = city;
//        this.notes = notes;
//        this.birthday = birthday;
//        this.vendorId = vendorId;
//        this.photo = photo;
//        this.apiCommunicator = apiCommunicator;
//    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
