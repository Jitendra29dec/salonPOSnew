package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddService;
import com.hubwallet.apptspos.APis.EditService;

import com.hubwallet.apptspos.APis.GetProductCategory;
import com.hubwallet.apptspos.APis.GetServiceByID;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetServiceByIDModel.GetServicesByData;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategory;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategoryData;
import com.hubwallet.apptspos.Utils.Models.TaxList;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddNewServices extends Fragment implements ApiCommunicator {
    private Spinner categorySpinner;
    private EditText serviceName,price,duration,commissionAmount,commissionPercent;
    AppCompatTextView save ,imgBack;
    private boolean isForEdit = false;
    private Communicator communicator;
    ApiCommunicator apiCommunicator;
    private String serviceId = "";
    AppCompatTextView title;
    RadioGroup rGroupTaxType,rGroupTax;
    RadioButton rBtnYes,rBtnNo;
    CheckBox chTax1,chTax2,chTax3;
    TextView titleTaxType;
    LinearLayout layoutTaxType;
    private String isTax ="0";
    private List<ProductCategoryData> categoryList;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            serviceId = getArguments().getString("service_id", "");
            if (!serviceId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", serviceId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_new_services, null, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        title = view.findViewById(R.id.ServicetTitle);
        rGroupTax = view.findViewById(R.id.rGroupTax);
        layoutTaxType = view.findViewById(R.id.layoutTaxType);
        titleTaxType = view.findViewById(R.id.titleTaxType);
        chTax1 = view.findViewById(R.id.chTax1);
        chTax2 = view.findViewById(R.id.chTax2);
        chTax3 = view.findViewById(R.id.chTax3);
        rBtnYes = view.findViewById(R.id.rBtnYes);
        rBtnNo = view.findViewById(R.id.rBtnNo);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        serviceName = view.findViewById(R.id.serviceNameEdiText);
        price = view.findViewById(R.id.priceEditText);
        duration = view.findViewById(R.id.durationEditText);
        commissionAmount = view.findViewById(R.id.commissionAmountEditText);
        commissionPercent = view.findViewById(R.id.commissionPercentEditText);
        save = view.findViewById(R.id.submitButton);
        imgBack = view.findViewById(R.id.imgBack);
        if (isForEdit){
            title.setText("Edit Service");
            save.setText("Update");
        }
        MySingleton.getInstance(getContext()).addToRequestQue(new GetProductCategory(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rBtnYes.isChecked()) {
                    isTax = "1";
                } else {
                    isTax = "0";
                }
                String  categoryId;
                categoryId = categoryList.get(categorySpinner.getSelectedItemPosition()).getCategoryId();
                List<TaxList> taxList = new ArrayList<>();

                if (chTax1.isChecked()) {
                    TaxList list = new TaxList();
                    list.setTax_id("1");
                    list.setTax_type("1");
                    list.setTax_rate("3%");
                    taxList.add(list);
                }
                if (chTax2.isChecked()) {
                    TaxList list2 = new TaxList();
                    list2.setTax_id("2");
                    list2.setTax_type("2");
                    list2.setTax_rate("4%");
                    taxList.add(list2);
                }
                if (chTax3.isChecked()) {
                    TaxList list2 = new TaxList();
                    list2.setTax_id("3");
                    list2.setTax_type("3");
                    list2.setTax_rate("5%");
                    taxList.add(list2);
                }
                ArrayList<HashMap<String, String>> taxData = new ArrayList<>();
                for (TaxList tax:taxList) {
                    HashMap<String, String> taxDataMap = new HashMap<>();
                    taxDataMap.put("tax_id", tax.getTax_id());
                    taxDataMap.put("tax_type", tax.getTax_type());
                    taxDataMap.put("tax_rate", tax.getTax_rate());
                    taxData.add(taxDataMap);
                }
                Gson json = new Gson();
                String tax = json.toJson(taxData);
               // Log.d(TAG, "onClick: data of day : " + tax);
                if (isForEdit) {
                    MySingleton.getInstance(getContext())
                            .addToRequestQue(new EditService(new PrefManager(getContext())
                                    .getVendorId(),serviceId,categoryId,getString(serviceName),
                                     getString(price),
                                     getString(duration),getString(commissionAmount),getString(commissionPercent),isTax,tax,apiCommunicator).getStringRequest());
                } else {
                    MySingleton.getInstance(getContext())
                            .addToRequestQue(new AddService(new PrefManager(getContext())
                                    .getVendorId(),categoryId, getString(serviceName),
                                    getString(price),
                                    getString(duration),getString(commissionAmount),getString(commissionPercent),isTax,tax,apiCommunicator).getStringRequest());
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new ServicesListFragment())
                        .commit();
            }
        });

        rGroupTax.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rBtnYes.isChecked()){
                    titleTaxType.setVisibility(View.VISIBLE);
                    layoutTaxType.setVisibility(View.VISIBLE);
                }else if (rBtnNo.isChecked()){
                    titleTaxType.setVisibility(View.GONE);
                    layoutTaxType.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("get_product_category")) {
            categoryList = new GsonBuilder().create().fromJson(response, ProductCategory.class).getResult();
            ArrayList<String> list = new ArrayList<>();
            for (ProductCategoryData b : categoryList) {
                list.add(b.getCategoryName());
            }
            setSpinnerBrand(list, categorySpinner);
            if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
                MySingleton.getInstance(getContext()).addToRequestQue(new GetServiceByID(new PrefManager(getContext()).getVendorId(),serviceId, apiCommunicator).getStringRequest());
            }
        }
        if (status.equalsIgnoreCase("get_service_by_id")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetServicesByData service = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetServicesByData.class);
                updatate(service);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (status.equalsIgnoreCase("service_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("services", "");
        }

        if (status.equalsIgnoreCase("service_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("services", "");
        }
    }

    private String getString(EditText name) {
        String val = name.getText().toString();
        return val;
    }

    private void setSpinnerBrand(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }


    private void updatate(GetServicesByData services) {
        serviceName.setText(services.getServiceName());
        price.setText(services.getServicePrice());
        duration.setText(services.getServiceDuration());
        commissionAmount.setText(services.getCommission_amount());
        commissionPercent.setText(services.getCommission_percent());
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryId().equalsIgnoreCase(services.getCategoryId())) {
                categorySpinner.setSelection(i);
            }
        }

        if (services.getTax_id().equalsIgnoreCase("1")) {
          rBtnYes.setChecked(true);
        }else{
            rBtnNo.setChecked(true);
        }
    }

}
