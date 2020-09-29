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

public class EditAppointment {

//    private final String vendorId;
//    private final String appointmentDate;
//    private final String serviceStylist;
//    private final String duration;
//    private final String note;
//    private final String customerId;
    private final ApiCommunicator apiCommunicator;
//    private String appointmetnID;
//    private String time;
    private Map<String, String> param = new HashMap<>();

    private StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.editAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                apiCommunicator.getApiData("edit_appointment", jsonObject.getString("message"));
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
            Map<String, String> params = param;
//            param.put("vendor_id", vendorId);
//            param.put("appointment_date", appointmentDate);
//            param.put("duration", duration);
//            param.put("note", note);
//            param.put("customer_id", customerId);
//            param.put("service_stylist", serviceStylist);
//            param.put("appointment_id", appointmetnID);
//            param.put("appointment_time", time);
            return params;
        }
    };

    public EditAppointment(String appointmetnID, String vendorId, String appointmentDate,
                           String serviceId,String stylistId, String duration, String note,
                           String customerId, String time, String timeEnd,
                           ApiCommunicator apiCommunicator) {
//        this.appointmetnID = appointmetnID;
//        vendor_id,  appointment_id, appointment_date, appointment_time, appointment_time_end, service_id, stylist_id
        param.put("vendor_id", vendorId);
        param.put("appointment_id", appointmetnID);
        param.put("appointment_date", appointmentDate);
        param.put("appointment_time", time);
        param.put("appointment_time_end", timeEnd);
        param.put("note", note);
        param.put("service_id", serviceId);
        param.put("stylist_id", stylistId);
//        this.vendorId = vendorId;
//        this.appointmentDate = appointmentDate;
//        this.serviceStylist = serviceStylist;
//        this.duration = duration;
//        this.note = note;
//        this.customerId = customerId;
//        this.time = time;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
