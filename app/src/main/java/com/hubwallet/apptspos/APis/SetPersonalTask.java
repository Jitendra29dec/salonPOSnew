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

public class SetPersonalTask {
    private final String stylsitId;
    private final String typeId;
    private final String fromTimes;
    private final String toTimes;
    private final String date;
    private final String blockBooking;
    private final String comment;
    private ApiCommunicator apiCommunicator;
    private String dayOff;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.setPersonalTask, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                apiCommunicator.getApiData("personal_task_status", jsonObject.getString("message"));
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
            param.put("stylist_id", stylsitId);
            param.put("type_id", typeId);
            param.put("task_date", date);
            if (!dayOff.isEmpty()) {
                param.put("day_off", dayOff);
            } else {
                param.put("task_time_from", fromTimes);
                param.put("task_time_to", toTimes);

            }
            param.put("block_booking", blockBooking);
            param.put("comment", comment);
            Log.e("getParams: ", param.toString());
            return param;

        }
    };

    public SetPersonalTask(ApiCommunicator apiCommunicator, String stylsitId, String typeId, String fromTimes, String toTimes, String date, String blockBooking, String comment, String dayOff) {

        this.apiCommunicator = apiCommunicator;
        this.stylsitId = stylsitId;
        this.typeId = typeId;
        this.fromTimes = fromTimes;
        this.toTimes = toTimes;
        this.date = date;
        this.blockBooking = blockBooking;
        this.comment = comment;
        this.dayOff = dayOff;
        //Log.e( "SetPersonalTask: ", stylsitId+" "+typeId+" "+fromTimes+" "+toTimes+" "+date+" "+blockBooking+" "+comment+" "+dayOff);
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
