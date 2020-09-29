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
import com.hubwallet.apptspos.APis.GetProductBrand;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.BrandListAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.BrandModel.GetBrand;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrandListFragment extends Fragment implements ApiCommunicator {

    @BindView(R.id.searchBrandEditText)
    EditText searchBrandEditText;
    @BindView(R.id.recyclerViewListItem)
    RecyclerView recyclerView;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    private BrandListAdapter adapter;
    Button btnAddBrand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_brand_list, null, false);
        ButterKnife.bind(this, v);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        btnAddBrand = v.findViewById(R.id.btnAddBrand);
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetProductBrand(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        searchBrandEditText.addTextChangedListener(new TextWatcher() {
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
        btnAddBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.productMainSubMenu, new AddBrandFragment())
                        .commit();
            }
        });
        return v;

    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_product_brand")) {
            List<BrandData> list = new GsonBuilder().create().fromJson(response, GetBrand.class).getMessage();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("brand_id")) {
            communicator.sendmessage("brand_added", response);
        }

    }

    private void setAdapter(List<BrandData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new BrandListAdapter(getContext(), list, apiCommunicator);
        recyclerView.setAdapter(adapter);
       // recyclerView.setAdapter(new BrandListAdapter(getContext(), list, apiCommunicator));
    }
}
