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

public class EditCategory {
    private final String categoryId;
    private String categoryName;
    private String vendorID;
    private String description;
    private String photo;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.editCategory, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("category_edited", jsonObject.getString("message"));
                    Log.e("onResponse: ", "called");
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
            param.put("category_name", categoryName);
            param.put("vendor_id", vendorID);
            param.put("category_id",categoryId);
            param.put("description", description);
           // param.put("photo",photo);
            return param;
        }
    };

    public EditCategory(String vendorID, String categoryId, String categoryName, String description,String photo, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.apiCommunicator = apiCommunicator;
        this.description = description;
        this.photo = photo;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
