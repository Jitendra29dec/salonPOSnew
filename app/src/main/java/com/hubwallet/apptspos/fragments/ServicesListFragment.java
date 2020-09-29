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
import com.hubwallet.apptspos.APis.GetServices;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.ServiceListAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;


public class ServicesListFragment extends Fragment implements ApiCommunicator {
    private EditText search;
    private RecyclerView recyclerView;
    private Button addNewservice;
    private ServiceListAdapter adapter;
    ApiCommunicator apiCommunicator;
    Communicator communicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services_list, null, false);
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        search = view.findViewById(R.id.searchServiceEditText);
        addNewservice = view.findViewById(R.id.btnAddServices);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetServices(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        search.addTextChangedListener(new TextWatcher() {
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

        addNewservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new AddNewServices())
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("services_list")) {
            List<GetServicesData> list = new GsonBuilder().create().fromJson(response, GetServicesList.class).getResult();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("service_id")) {
            communicator.sendmessage("services", response);
        }

    }

    private void setAdapter(List<GetServicesData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new ServiceListAdapter(getContext(), list, apiCommunicator);
        // recyclerView.setAdapter(new CategoryListAdapter(getContext(), list, apiCommunicator));
        recyclerView.setAdapter(adapter);
    }

}
