package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetProductBrand;
import com.hubwallet.apptspos.APis.GetServiceCategory;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.BrandListAdapter;
import com.hubwallet.apptspos.Adapters.ServiceCategoryAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.BrandModel.GetBrand;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategory;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategoryData;
import com.hubwallet.apptspos.Utils.Models.ServiceCategoryData;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import butterknife.BindView;

public class ListServiceCategory extends Fragment implements ApiCommunicator {
    RecyclerView recyclerView;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    private ServiceCategoryAdapter adapter;
    Button btnAddCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_service_category, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        btnAddCategory = view.findViewById(R.id.btnAddCategory);
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetServiceCategory(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new AddNewServiceCategory())
                        .commit();
            }
        });
        return view;
    }

    private void setAdapter(List<ProductCategoryData> list) {
      //  recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new ServiceCategoryAdapter(getContext(), list, apiCommunicator);
        recyclerView.setAdapter(adapter);
        // recyclerView.setAdapter(new BrandListAdapter(getContext(), list, apiCommunicator));
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_service_category")) {
            List<ProductCategoryData> list = new GsonBuilder().create().fromJson(response, ProductCategory.class).getResult();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("category_id")) {
            communicator.sendmessage("serviceCat_added", response);
        }
    }
}
