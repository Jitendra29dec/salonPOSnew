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
import com.hubwallet.apptspos.APis.GetPurchaseOrder;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.PurchaseOrderAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.BrandModel.GetBrand;
import com.hubwallet.apptspos.Utils.Models.GetPurchaseOrderData;
import com.hubwallet.apptspos.Utils.Models.GetPurchaseOrderResponse;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchaseOrderList extends Fragment implements ApiCommunicator {
    @BindView(R.id.recyclerViewListItem)
    RecyclerView recyclerView;
    @BindView(R.id.btnAddPO)
    Button addPo;
    ApiCommunicator apiCommunicator;
    Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_purchase_order_list, null, false);
        ButterKnife.bind(this, v);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        StringRequest stringRequest = new GetPurchaseOrder(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);

        addPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.productMainSubMenu, new AddPurchaseOrderFragment())
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_purchase_order")) {
            List<GetPurchaseOrderData> list = new GsonBuilder().create().fromJson(response, GetPurchaseOrderResponse.class).getResult();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("po_id")) {
            communicator.sendmessage("po_added", response);
        }

    }

    private void setAdapter(List<GetPurchaseOrderData> list) {
        // recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new PurchaseOrderAdapter(getContext(), list, apiCommunicator));
    }
}
