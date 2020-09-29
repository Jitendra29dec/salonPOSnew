package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddServiceCategory;
import com.hubwallet.apptspos.APis.EditServiceCategory;
import com.hubwallet.apptspos.APis.GetBrandById;
import com.hubwallet.apptspos.APis.GetServieCategoryById;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetBrandByData;
import com.hubwallet.apptspos.Utils.Models.GetCategoryByData;
import com.hubwallet.apptspos.Utils.Models.ServiceCategoryData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewServiceCategory extends Fragment implements ApiCommunicator {

    private AppCompatTextView submitBtn, backBtn;
    private EditText categoryName;
    private Communicator communicator;
    ApiCommunicator apiCommunicator;
    private boolean isForEdit = false;
    private String categoryId = "";
    AppCompatTextView brandTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString("category_id", "");
            if (!categoryId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", categoryId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_service_category, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        brandTitle = view.findViewById(R.id.brandTitle);
        categoryName = view.findViewById(R.id.categoryEdiText);
        submitBtn = view.findViewById(R.id.submitButton);
        backBtn = view.findViewById(R.id.imgBack);

        if (isForEdit) {
            brandTitle.setText("Edit Category");
            submitBtn.setText("Update");
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new ListServiceCategory())
                        .commit();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!categoryName.getText().toString().isEmpty() && categoryName.getText().toString().length() > 0) {
                    ((NavigationActivity) getActivity()).onProgress();
                    if (isForEdit) {
                        MySingleton.getInstance(getContext())
                                .addToRequestQue(new EditServiceCategory(categoryId, categoryName.getText().toString(), apiCommunicator).getStringRequest());
                    } else {
                        MySingleton.getInstance(getContext())
                                .addToRequestQue(new AddServiceCategory(new PrefManager(getContext())
                                        .getVendorId(), categoryName.getText().toString(), apiCommunicator).getStringRequest());
                    }
                } else {
                    categoryName.setError("Enter category name");
                }

            }
        });
        if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
            MySingleton.getInstance(getContext()).addToRequestQue(new GetServieCategoryById(apiCommunicator, categoryId).getStringRequest());
        }

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("service_cat_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("serviceCat_added", "");
        }

        if (status.equalsIgnoreCase("service_cat_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("serviceCat_added", "");
        }

        if (status.equalsIgnoreCase("get_catService_byId")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetCategoryByData serviceCategoryData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetCategoryByData.class);
                updatate(serviceCategoryData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatate(GetCategoryByData data) {
        categoryName.setText(data.getCategoryName());
    }
}
