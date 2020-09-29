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

import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetDiscountList;
import com.hubwallet.apptspos.APis.GetProducts;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.DiscountListAdapter;
import com.hubwallet.apptspos.Adapters.ProductsAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.Discount.DiscountData;
import com.hubwallet.apptspos.Utils.Models.Discount.DiscountResult;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductData;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;


public class DiscountFragment extends Fragment implements ApiCommunicator {
    private RecyclerView recyclerView;
    private DiscountListAdapter adapter;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    private Button btnAddDiscount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_discount, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnAddDiscount = view.findViewById(R.id.btnAddDiscount);
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetDiscountList( new PrefManager(getContext()).getVendorId(),apiCommunicator).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);

        btnAddDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new AddNewDiscount())
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("discount_list")) {
            List<DiscountData> list = new GsonBuilder().create().fromJson(response, DiscountResult.class).getResult();
            setAdapter(list);
        }
        if (status.equalsIgnoreCase("discount_id")) {
            communicator.sendmessage("discount", response);
        }
    }

    private void setAdapter(List<DiscountData> list) {
        //  recyclerView.setAdapter(new ProductsAdapter(getContext(), list, apiCommunicator));
        adapter = new DiscountListAdapter(getContext(), list, apiCommunicator);
        recyclerView.setAdapter(adapter);
    }
}
