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

public class AddCoupon {
    private String couponFor;
    private String vendorID;
    private String description;
    private String couponType;
    private String amount;
    private String percent;
    private String validFrom;
    private String validTill;
    private String couponCode;
    private String customCouponCode;
    private String startTime;
    private String endTime;
    private String templateId;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.addCoupon, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("coupon_added", jsonObject.getString("message"));
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
            param.put("coupon_for", couponFor);
            param.put("vendor_id", vendorID);
            param.put("coupon_type", couponType);
            param.put("amount", amount);
            param.put("percent", percent);
            param.put("valid_from", validFrom);
            param.put("valid_till", validTill);
            param.put("description", description);
            param.put("coupon_code", couponCode);
            param.put("custom_coupon_code", customCouponCode);
            param.put("start_time", startTime);
            param.put("end_time", endTime);
            param.put("template_id ",templateId);
            return param;
        }
    };

    public AddCoupon(String vendorID, String couponFor, String couponType, String amount,String percent,String validFrom, String validTill,String description,String couponCode,
                     String customCouponCode,String startTime,String endTime,String templateId,ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.couponFor = couponFor;
        this.apiCommunicator = apiCommunicator;
        this.description = description;
        this.couponType = couponType;
        this.amount =amount;
        this.percent = percent;
        this.validFrom = validFrom;
        this.validTill = validTill;
        this.couponCode = couponCode;
        this.customCouponCode = customCouponCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.templateId =templateId;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
