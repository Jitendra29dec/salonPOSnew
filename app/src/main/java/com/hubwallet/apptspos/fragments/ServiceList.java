package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetServices;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.AdapterGetServices;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceList extends Fragment implements ApiCommunicator {
    ApiCommunicator apiCommunicator;
    RecyclerView recyclerView;
    NavigationActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiCommunicator = this;
        activity = (NavigationActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_list_fragment, null, false);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetServices(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        recyclerView = view.findViewById(R.id.recyclerViewServices);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        activity.offProgress();
        if (status.equalsIgnoreCase("services_list")) {
            List<GetServicesData> list = new GsonBuilder().create().fromJson(response, GetServicesList.class).getResult();
            recyclerView.setAdapter(new AdapterGetServices(getContext(), list, apiCommunicator));
        }
        if (status.equalsIgnoreCase("service_id")) {
            Communicator communicator = (Communicator) getActivity();
            assert communicator != null;
            communicator.sendmessage("services", response);
        }
        if (status.equalsIgnoreCase("delete_service")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            Communicator communicator = (Communicator) getActivity();
            assert communicator != null;
            communicator.sendmessage("services", "delete");
        }
    }
}
