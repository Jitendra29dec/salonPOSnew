package com.hubwallet.apptspos.APis;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddGroupAppointmnet {
    private String vendorId;
    private String depositId;
    private String appointmentDate;
    private String serviceId;
    private String stylistId;
    private String duration;
    private String note;
    private String captain;
    private String custmerId;
    private String appointmentTime;
    private ApiCommunicator apiCommunicator;
    private StringRequest stringRequest = new StringRequest(1, Constants.addGroupAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("addgroup: ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("success");
                if (status.equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("group_appointment", jsonObject.getString("message"));
                } else {
                    apiCommunicator.getApiData("0", jsonObject.getString("message"));
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
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("vendor_id", vendorId);
            hashMap.put("deposit_id", depositId);
            hashMap.put("appointment_date", appointmentDate);
            hashMap.put("appointment_time", appointmentTime);
            hashMap.put("service_id", serviceId);
            hashMap.put("stylist_id", stylistId);
            hashMap.put("duration", duration);
            hashMap.put("note", note);
            hashMap.put("customer_id", custmerId);
            hashMap.put("captain", captain);
            Log.e("getParams: ", hashMap.toString());
            return hashMap;
        }
    };

    public AddGroupAppointmnet(String vendorId,String depositId, String appointmentDate, String serviceId, String stylistId, String duration, String note, String captain, String custmerId, String appointmentTime, ApiCommunicator apiCommunicator) {
        this.vendorId = vendorId;
        this.depositId = depositId;
        this.appointmentDate = appointmentDate;
        this.serviceId = serviceId;
        this.stylistId = stylistId;
        this.duration = duration;
        this.note = note;
        this.captain = captain;
        this.custmerId = custmerId;
        this.appointmentTime = appointmentTime;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
