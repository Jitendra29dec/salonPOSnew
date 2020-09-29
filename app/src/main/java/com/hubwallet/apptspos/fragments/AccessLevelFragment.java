package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetAccessLevel;
import com.hubwallet.apptspos.APis.GetAccessLevelModule;
import com.hubwallet.apptspos.APis.GetServices;
import com.hubwallet.apptspos.APis.GetStateList;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.AcessLevelAdapter;
import com.hubwallet.apptspos.Adapters.ServiceListAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.AccessLevelModuleData;
import com.hubwallet.apptspos.Utils.Models.AccessLevelModuleList;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesList;
import com.hubwallet.apptspos.Utils.Models.SettingEmpData;
import com.hubwallet.apptspos.Utils.Models.SettingEmpList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;


public class AccessLevelFragment extends Fragment implements ApiCommunicator {
    private RecyclerView recyclerView,moduleList;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    private AcessLevelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_access_level, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        moduleList = view.findViewById(R.id.moduleList);
        moduleList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetAccessLevelModule(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());

        StringRequest stringRequest = new GetAccessLevel(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("settingEmp_list")) {
            List<SettingEmpData> list = new GsonBuilder().create().fromJson(response, SettingEmpList.class).getResult();
            setAdapter(list);
        }
        if (status.equalsIgnoreCase("module_list")) {
            List<AccessLevelModuleData> list = new GsonBuilder().create().fromJson(response, AccessLevelModuleList.class).getResult();
           // adapter.setModuleList(list);
        }

      /*  if (status.equalsIgnoreCase("service_id")) {
            communicator.sendmessage("services", response);
        }*/
    }

    private void setAdapter(List<SettingEmpData> list) {
        adapter = new AcessLevelAdapter(getContext(), list, apiCommunicator);
      //   recyclerView.setAdapter(new CategoryListAdapter(getContext(), list, apiCommunicator));
        recyclerView.setAdapter(adapter);
    }

}
