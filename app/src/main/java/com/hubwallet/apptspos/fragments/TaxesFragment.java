package com.hubwallet.apptspos.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetTaxes;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateTax;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.ProductTaxData;
import com.hubwallet.apptspos.Utils.Models.ServiceTaxData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class TaxesFragment extends Fragment implements ApiCommunicator {
    private AppCompatTextView submitButton;
    AppCompatTextView tvProductTax1;
    private TextView tvProductTax2, tvProductTax3, tvServiceTax1, tvServiceTax2, tvServiceTax3, tvStartDate1, tvStartDate2, tvStartDate3,
            tvDes1, tvDes2, tvDes3, startDate1, startDate2, startDate3;
    private EditText productTax1, productTax2, productTax3, serviceTax1, serviceTax2, serviceTax3,
            des1, des2, des3;
    private String pTax1, pTax2, pTax3, sTax1, sTax2, sTax3,description;
    private String date, date1, date2;
    ApiCommunicator apiCommunicator;
    String productJson, serviceJson;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taxes, container, false);
        apiCommunicator = this;
        calendar = Calendar.getInstance();
        intView(view);
        ((NavigationActivity) getActivity()).onProgress();
        MySingleton.getInstance(getContext()).addToRequestQue(new GetTaxes(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pTax1 = productTax1.getText().toString();
                pTax2 = productTax2.getText().toString();
                pTax3 = productTax3.getText().toString();
                sTax1 = serviceTax1.getText().toString();
                sTax2 = serviceTax2.getText().toString();
                sTax3 = serviceTax3.getText().toString();
                description =des1.getText().toString();
                jsonResultForProduct(pTax1, pTax2, pTax3,description);
                jsonResultForService(sTax1, sTax2, sTax3,description);
                ((NavigationActivity) getActivity()).onProgress();
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new UpdateTax(new PrefManager(getContext())
                                .getVendorId(), serviceJson, productJson, apiCommunicator).getStringRequest());
            }
        });
        return view;
    }

    private void intView(View view) {
        productTax1 = view.findViewById(R.id.productTax1);
        productTax2 = view.findViewById(R.id.productTax2);
        productTax3 = view.findViewById(R.id.productTax3);
        serviceTax1 = view.findViewById(R.id.serviceTax1);
        serviceTax2 = view.findViewById(R.id.serviceTax2);
        serviceTax3 = view.findViewById(R.id.serviceTax3);
        startDate1 = view.findViewById(R.id.startDateTax1);
        startDate2 = view.findViewById(R.id.startDateTax2);
        startDate3 = view.findViewById(R.id.startDateTax3);
        des1 = view.findViewById(R.id.desTax1);
        des2 = view.findViewById(R.id.desTax2);
        des3 = view.findViewById(R.id.desTax3);

        submitButton = view.findViewById(R.id.submitButton);
        tvProductTax1 = view.findViewById(R.id.tvProductTax1);
        tvProductTax2 = view.findViewById(R.id.tvProductTax2);
        tvProductTax3 = view.findViewById(R.id.tvProductTax3);
        tvServiceTax1 = view.findViewById(R.id.tvServiceTax1);
        tvServiceTax2 = view.findViewById(R.id.tvServiceTax2);
        tvServiceTax3 = view.findViewById(R.id.tvServiceTax3);
        tvStartDate1 = view.findViewById(R.id.tvStartDateTax1);
        tvStartDate2 = view.findViewById(R.id.tvStartDateTax2);
        tvStartDate3 = view.findViewById(R.id.tvStartDateTax3);
        tvDes1 = view.findViewById(R.id.tvDesTax1);
        tvDes2 = view.findViewById(R.id.tvDesTax2);
        tvDes3 = view.findViewById(R.id.tvDesTax3);

        startDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    startDate1.setText(date);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        startDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear1 = calendar.get(Calendar.YEAR);
                int mMonth1 = calendar.get(Calendar.MONTH);
                int mDay1 = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    date1 = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    startDate2.setText(date1);
                }, mYear1, mMonth1, mDay1);
                datePickerDialog1.show();
            }
        });

        startDate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear2 = calendar.get(Calendar.YEAR);
                int mMonth2 = calendar.get(Calendar.MONTH);
                int mDay2 = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    date2 = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    startDate3.setText(date2);
                }, mYear2, mMonth2, mDay2);
                datePickerDialog2.show();
            }
        });

    }


    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("update_tax")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            MySingleton.getInstance(getContext()).addToRequestQue(new GetTaxes(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
           /* try {
                JSONObject jsonObject = new JSONObject(response);
                ProductTaxData productTaxData = new GsonBuilder().create().fromJson(jsonObject.getString("product"), ProductTaxData.class);
                ServiceTaxData serviceTaxData = new GsonBuilder().create().fromJson(jsonObject.getString("service"), ServiceTaxData.class);
                getDataFromApi(productTaxData);
                getDataFromApi1(serviceTaxData);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
        if (status.equalsIgnoreCase("get_taxes")) {
            //  Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject(response);
                ProductTaxData productTaxData = new GsonBuilder().create().fromJson(jsonObject.getString("product"), ProductTaxData.class);
                ServiceTaxData serviceTaxData = new GsonBuilder().create().fromJson(jsonObject.getString("service"), ServiceTaxData.class);
                getDataFromApi(productTaxData);
                getDataFromApi1(serviceTaxData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDataFromApi1(ServiceTaxData serviceTaxData) {
        tvServiceTax1.setText(serviceTaxData.getTax1());
        tvServiceTax2.setText(serviceTaxData.getTax2());
        tvServiceTax3.setText(serviceTaxData.getTax3());
    }

    private void getDataFromApi(ProductTaxData productTaxData) {
        tvProductTax1.setText(productTaxData.getTax1());
        tvProductTax2.setText(productTaxData.getTax2());
        tvProductTax3.setText(productTaxData.getTax3());
        tvStartDate1.setText(productTaxData.getStartDate());
        tvStartDate2.setText(productTaxData.getStartDate());
        tvStartDate3.setText(productTaxData.getStartDate());
        tvDes1.setText(productTaxData.getDescription());
        tvDes2.setText(productTaxData.getDescription());
        tvDes3.setText(productTaxData.getDescription());
    }

    //String tax1,String tax2, String tax3,String description
    private void jsonResultForProduct(String tax1, String tax2, String tax3,String description) {
        JSONObject jsonResult = new JSONObject();
        try {
            jsonResult.put("tax1", tax1);
            jsonResult.put("tax2", tax2);
            jsonResult.put("tax3", tax3);
            jsonResult.put("description", description);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*Gson gson = new Gson();*/
        productJson = jsonResult.toString();
        Log.d("DEV", "jsonResult->" + jsonResult);
       /* JSONObject json = null;
        try {
            json = new JSONObject("{\"" + "tax1" + "\":" + "\"" + "4.00"+ "\"" + "," + "\"" + "tax2" + "\":" + "5.00" + "," + "\"" + "tax3"
                    + "\":" + "\"" + "9.0"+ "\"" + "description" + "\":" + "Test" + "," + "\"" + "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String str_json = gson.toJson(json);
        Log.d("JSon",str_json);*/
    }

    private void jsonResultForService(String tax1, String tax2, String tax3,String description) {
        JSONObject jsonResult = new JSONObject();
        try {
            jsonResult.put("tax1", tax1);
            jsonResult.put("tax2", tax2);
            jsonResult.put("tax3", tax3);
            jsonResult.put("description", description);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //  Gson gson = new Gson();
        serviceJson = jsonResult.toString();
        Log.d("DEV", "jsonResult->" + jsonResult.toString());
    }
}
