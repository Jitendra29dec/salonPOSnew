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

public class UpdateNotification {
    private String confirmation;
    private String appointDetail;
    private String confirmationTime;
    private String appointmentReminder;
    private String appointmentReminderTime;
    private String businessNotification;
    private String stylistNotification;
    private String vendorID;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.getUpdateNotification, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("update_notification", jsonObject.getString("message"));
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
            param.put("appointment_detail", appointDetail);
            param.put("confirmation", confirmation);
            param.put("confirmation_time", confirmationTime);
            param.put("appointment_reminder", appointmentReminder);
            param.put("appointment_reminder_time", appointmentReminderTime);
            param.put("business_notification", businessNotification);
            param.put("stylist_notification", stylistNotification);
            return param;
        }
    };

    public UpdateNotification(String vendorID, String appointDetail, String confirmation, String confirmationTime, String appointmentReminder, String appointmentReminderTime,
                              String businessNotification, String stylistNotification, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.appointDetail = appointDetail;
        this.confirmation = confirmation;
        this.confirmationTime = confirmationTime;
        this.appointmentReminder = appointmentReminder;
        this.appointmentReminderTime = appointmentReminderTime;
        this.businessNotification = businessNotification;
        this.stylistNotification = stylistNotification;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
