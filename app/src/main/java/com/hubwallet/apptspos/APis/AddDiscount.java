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

public class AddDiscount {
    private String discountFor;
    private String vendorID;
    private String description;
    private String discountType;
    private String discount;
    private String startDate;
    private String endDate;
    private String minAmount;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.addDiscount, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("discount_added", jsonObject.getString("message"));
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
            param.put("discount_for", discountFor);
            param.put("vendor_id", vendorID);
            param.put("description", description);
            param.put("discount_type", discountType);
            param.put("discount", discount);
            param.put("start_date", startDate);
            param.put("end_date", endDate);
            param.put("min_amount", minAmount);
            param.put("image", "");
            return param;

        }
    };

    public AddDiscount(String vendorID, String discountFor, String description,String discountType,String discount,String startDate,String endDate,String minAmount, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.discountFor = discountFor;
        this.apiCommunicator = apiCommunicator;
        this.description = description;
        this.discountType = discountType;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minAmount = minAmount;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
