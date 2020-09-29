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
import com.hubwallet.apptspos.APis.GetCoupon;
import com.hubwallet.apptspos.APis.GetDiscountList;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.CouponListAdapter;
import com.hubwallet.apptspos.Adapters.DiscountListAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.Coupon.CouponData;
import com.hubwallet.apptspos.Utils.Models.Coupon.CouponResult;
import com.hubwallet.apptspos.Utils.Models.Discount.DiscountData;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductData;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;


public class CouponFragment extends Fragment implements ApiCommunicator {
    private RecyclerView recyclerView;
    private CouponListAdapter adapter;
    private Button btnAddCategory;
    ApiCommunicator apiCommunicator;
    Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetCoupon( new PrefManager(getContext()).getVendorId(),apiCommunicator).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        btnAddCategory = view.findViewById(R.id.btnAddCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new AddCouponFragment())
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("coupon_list")) {
            List<CouponData> list = new GsonBuilder().create().fromJson(response, CouponResult.class).getResult();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("coupon_id")) {
            communicator.sendmessage("coupon_added", response);
        }

    }

    private void setAdapter(List<CouponData> list) {
        //  recyclerView.setAdapter(new ProductsAdapter(getContext(), list, apiCommunicator));
        adapter = new CouponListAdapter(getContext(), list, apiCommunicator);
        recyclerView.setAdapter(adapter);
    }
}
