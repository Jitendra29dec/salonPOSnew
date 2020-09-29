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

public class AddPo {
    private  String supplierId;
    private String poDate;
    private String poNumber;
    private String note;
    private String productDetail;
    private String vendorID;
    private ApiCommunicator apiCommunicator;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.addPO, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    apiCommunicator.getApiData("add_po", jsonObject.getString("message"));
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
            param.put("supplier_id", supplierId);
            param.put("po_date",poDate);
            param.put("po_number",poNumber);
            param.put("note",note);
            param.put("product_detail",productDetail);
            return param;
        }
    };

    public AddPo(String vendorID, String supplierId,String poDate,String poNumber,String note,String productDetails, ApiCommunicator apiCommunicator) {
        this.vendorID = vendorID;
        this.supplierId = supplierId;
        this.poDate = poDate;
        this.poNumber = poNumber;
        this.note = note;
        this.productDetail = productDetails;
        this.apiCommunicator = apiCommunicator;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }
}
