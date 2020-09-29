package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetReturnPurchaseOrder;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Adapters.ReturnPurchaseOrderAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.BrandModel.GetBrand;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReturnPurchaseOrderList extends Fragment implements ApiCommunicator {
    @BindView(R.id.recyclerViewListItem)
    RecyclerView recyclerView;
    ApiCommunicator apiCommunicator;
    Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_return_purchase_order_list, null, false);
        ButterKnife.bind(this, v);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        StringRequest stringRequest = new GetReturnPurchaseOrder(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        return v;
    }

    @Override
    public void getApiData(String status, String response) {
        List<BrandData> list = new GsonBuilder().create().fromJson(response, GetBrand.class).getMessage();
        setAdapter(list);
    }

    private void setAdapter(List<BrandData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new ReturnPurchaseOrderAdapter(getContext(), list, apiCommunicator));
    }
}
