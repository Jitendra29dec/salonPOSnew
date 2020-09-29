package com.hubwallet.apptspos.APis;


import android.util.Log;

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

public class AddAppointment {


    //    private final String vendor_id;
//    private final String appointment_day;
//    private final String serviceStylist;
//    private final String duration;
//    private final String note;
//    private final String customer_id;
    private ApiCommunicator apiCommunicator;
    private String time;
    private Map<String, String> params = new HashMap<>();

    private StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.addAppointment, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("add Appoint", response);
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("appointment_add_1", jsonObject.getString("message"));
                } else {
                    {
                        apiCommunicator.getApiData("appointment_add_0", jsonObject.getString("message"));
                    }
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
            Map<String, String> param = params;
//            param.put("vendor_id", vendor_id);
//            param.put("appointment_date", appointment_day);
//            param.put("service", serviceStylist);
//            param.put("duration", "100");
//            param.put("note", note);
//            param.put("appointment_time", time);
//            param.put("customer_id", customer_id);

            return param;
        }
    };

    public AddAppointment(String vendor_Id, String customer_id,
                          String serviceId, String stylistId,
                          String appointment_date, String timeStart,
                          String timeEnd,
                          String note, String price, String duration,
                          ApiCommunicator apiCommunicator) {
//        vendor_id, service, stylist, appointment_date, appointment_time, customer, appointment_note, price, duration
        params.put("vendor_id", vendor_Id);
        params.put("service", serviceId);
        params.put("stylist", stylistId);
        params.put("appointment_date", appointment_date);
        params.put("appointment_time", timeStart);
//        params.put("appointment_time_end", timeEnd);
        params.put("customer", customer_id);
        params.put("appointment_note", note);
        params.put("price", price);
        params.put("duration", duration);
//        params.put("duration", duration);
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
