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

public class GetCategoryById {
    private String vendor_id;
    private  String category_id;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.getCategoryById, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("get_category_byId", response);
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
            Map<String, String> param = new HashMap<>();
            param.put("vendor_id", vendor_id);
            param.put("category_id",category_id);
            return param;
        }
    };


    public GetCategoryById(ApiCommunicator apiCommunicator,String vendor_id,String category_id) {
        this.vendor_id = vendor_id;
        this.category_id = category_id;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }

}
