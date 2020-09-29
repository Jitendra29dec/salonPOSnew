package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;

import com.hubwallet.apptspos.APis.GetProductCategory;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.CategoryListAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategory;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategoryData;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListFragment extends Fragment implements ApiCommunicator {
    @BindView(R.id.recyclerViewListItem)
    RecyclerView recyclerView;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    Button btnAddCategory;
    EditText searchCategoryEditText;
    private CategoryListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_category_list, null, false);
        ButterKnife.bind(this, v);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        btnAddCategory = v.findViewById(R.id.btnAddCategory);
        searchCategoryEditText = v.findViewById(R.id.searchCategoryEditText);
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetProductCategory(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        searchCategoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.productMainSubMenu, new AddCategoryFragment())
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_product_category")) {
            List<ProductCategoryData> list = new GsonBuilder().create().fromJson(response, ProductCategory.class).getResult();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("category_id")) {
            communicator.sendmessage("category_added", response);
        }


    }

    private void setAdapter(List<ProductCategoryData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter= new CategoryListAdapter(getContext(), list, apiCommunicator);
        recyclerView.setAdapter(adapter);
       // recyclerView.setAdapter(new CategoryListAdapter(getContext(), list, apiCommunicator));
    }
}
