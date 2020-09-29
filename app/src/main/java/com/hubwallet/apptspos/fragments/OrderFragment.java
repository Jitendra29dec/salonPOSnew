package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetOrders;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.OrderAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetOrderList.OrderData;
import com.hubwallet.apptspos.Utils.Models.GetOrderList.OrderList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderFragment extends Fragment implements ApiCommunicator {
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_order_fragment, null, false);
        recyclerView = v.findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MySingleton.getInstance(getContext()).addToRequestQue(new GetOrders(new PrefManager(getContext()).getVendorId(), this).getStringRequest());
        return v;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("order_list")) {
            List<OrderData> list = new GsonBuilder().create().fromJson(response, OrderList.class).getResult();
            setAdapter(list);
        }
        if (status.equalsIgnoreCase("get_order_detail")) {
            Communicator communicator = (Communicator) getActivity();
            communicator.sendmessage("order_detail", response);
        }
    }

    private void setAdapter(List<OrderData> list) {
        recyclerView.setAdapter(new OrderAdapter(getContext(), list, this));

    }
}
