package com.hubwallet.apptspos.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddPo;
import com.hubwallet.apptspos.APis.GetProductById;
import com.hubwallet.apptspos.APis.GetProductLList;
import com.hubwallet.apptspos.APis.GetVendor;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateCalendar;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.AddPoAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.ItemClick;
import com.hubwallet.apptspos.Utils.Models.AddPoModel;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductData;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductList;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierList;
import com.hubwallet.apptspos.Utils.Models.ProductModel.ProductData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class AddPurchaseOrderFragment extends Fragment implements ApiCommunicator, ItemClick {
    ApiCommunicator apiCommunicator;
    private List<SupplierData> vendorList;
    private List<GetProductData> productList;
    private Spinner vendorSpinner;
    private TextView poDate, addMorePO;
    private EditText poNumber, note;
    private Calendar calendar;
    private String fromDate;
    private AddPoAdapter addPoAdapter;
    private RecyclerView recyclerView;
    private AppCompatTextView submitButon;
    private AddPoModel addPoModel;
    ArrayList<String> addList = new ArrayList<>();
    private List<AddPoModel> mProductDetailList = new ArrayList<>();
    ArrayList<String> productItemsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_purchase_order, container, false);
        apiCommunicator = this;
        calendar = Calendar.getInstance();
        vendorSpinner = view.findViewById(R.id.vendorSpinner);
        poDate = view.findViewById(R.id.poDate);
        addMorePO = view.findViewById(R.id.addMorePO);
        poNumber = view.findViewById(R.id.poNumber);
        note = view.findViewById(R.id.note);
        submitButon = view.findViewById(R.id.submitButton);
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetVendor(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        MySingleton.getInstance(getContext()).addToRequestQue(new GetProductLList(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());

        poDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);

                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    fromDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    poDate.setText(fromDate);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        addMorePO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPoModel model = new AddPoModel();
              //  model.setProduct(productItemsList);
                addPoAdapter.updateProductItem(model);
            }
        });

        submitButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HashMap<String,String>> productData = new ArrayList<>();
                for (AddPoModel data:mProductDetailList){
                    HashMap<String,String> productDataMap = new HashMap<>();
                    productDataMap.put("product_id","1");
                    productDataMap.put("price",data.getRate());
                    productDataMap.put("quantity",data.getQuantity());
                    productDataMap.put("amount",data.getAmount());
                    productDataMap.put("tax_type",data.getTax());
                    productData.add(productDataMap);
                }
                Gson json = new Gson();
                String productDetails= json.toJson(productData);
              //  Log.d(TAG, "onClick: data of day : "+days);
                String supplierId = vendorList.get(vendorSpinner.getSelectedItemPosition() != 0 ? vendorSpinner.getSelectedItemPosition() - 1 : vendorSpinner.getSelectedItemPosition()).getSupplierId();
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new AddPo(new PrefManager(getContext())
                                .getVendorId(), supplierId, poDate.getText().toString(), poNumber.getText().toString(), note.getText().toString(), productDetails, apiCommunicator).getStringRequest());
            }
        });

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_supplier")) {
            vendorList = new GsonBuilder().create().fromJson(response, SupplierList.class).getMessage();
            ArrayList<String> list = new ArrayList<>();
            list.add("SELECT");
            for (SupplierData b : vendorList) {
                list.add(b.getSupplierName());
            }
            setSpinnerBrand(list, vendorSpinner);
        }

        if (status.equalsIgnoreCase("products_list")) {
            productList = new GsonBuilder().create().fromJson(response, GetProductList.class).getResult();
            productItemsList = new ArrayList<>();
            productItemsList.add("SELECT");
            for (GetProductData b : productList) {
                productItemsList.add(b.getProductName());
            }
            setList(productList);
        }
        if (status.equalsIgnoreCase("get_product_by_id")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetProductData productData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetProductData.class);
                 // addPoAdapter.updatefield(productData);
                  addPoModel.setQuantity(productData.getQuantity());
                  addPoModel.setRate(productData.getPriceRetail());
                  addPoAdapter.notifyDataSetChanged();
                 // addPoModel.setTax(productData.getT);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setSpinnerBrand(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }

    private void setList(List<GetProductData> productList) {
        addPoModel = new AddPoModel();
     //   addPoModel.setProduct(productList);
        mProductDetailList.add(addPoModel);
        addPoAdapter = new AddPoAdapter(getContext(), mProductDetailList,productList, apiCommunicator);
        recyclerView.setAdapter(addPoAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
      //  String supplierId = vendorList.get(vendorSpinner.getSelectedItemPosition() != 0 ? vendorSpinner.getSelectedItemPosition() - 1 : vendorSpinner.getSelectedItemPosition()).getSupplierId();

    }
}
