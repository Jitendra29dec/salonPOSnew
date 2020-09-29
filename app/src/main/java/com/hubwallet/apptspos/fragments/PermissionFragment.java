package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.request.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetAccessLevel;
import com.hubwallet.apptspos.APis.GetCouponById;
import com.hubwallet.apptspos.APis.GetIouAmount;
import com.hubwallet.apptspos.APis.GetPermissionById;
import com.hubwallet.apptspos.APis.GetPermissionLevel;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateLockTime;
import com.hubwallet.apptspos.APis.UpdatePermission;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.AcessLevelAdapter;
import com.hubwallet.apptspos.Adapters.EmpListAdapter;
import com.hubwallet.apptspos.Adapters.UserPermissionAdapter;
import com.hubwallet.apptspos.Adapters.UserPermissionEmployeeAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.EmployeeOnClick;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;
import com.hubwallet.apptspos.Utils.Models.CouponById.GetCouponData;
import com.hubwallet.apptspos.Utils.Models.EmployeePermissionData;
import com.hubwallet.apptspos.Utils.Models.EmployeePermissionList;
import com.hubwallet.apptspos.Utils.Models.GetPermissionData;
import com.hubwallet.apptspos.Utils.Models.GetPermissionList;
import com.hubwallet.apptspos.Utils.Models.SettingEmpData;
import com.hubwallet.apptspos.Utils.Models.SettingEmpList;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PermissionFragment extends Fragment implements ApiCommunicator, EmployeeOnClick {
    private RecyclerView recyclerView, permissionList;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    private Button btnUpdatePermission;
    private UserPermissionAdapter adapter;
    private UserPermissionEmployeeAdapter permissionAdapter;
    private String stylistId ="1";
    private List<EmployeePermissionData> permissionDatalist;
    private static final String TAG = PermissionFragment.class.getSimpleName();
    ArrayList <String> checkList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_permission, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        recyclerView = view.findViewById(R.id.empList);
        permissionList = view.findViewById(R.id.permissionList);
        btnUpdatePermission = view.findViewById(R.id.btnUpdatePermission);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        permissionList.setLayoutManager(layoutManager1);

        StringRequest stringRequest = new GetAccessLevel(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        StringRequest stringRequest1 = new GetPermissionLevel(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        stringRequest1.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest1);
        btnUpdatePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).onProgress();
                ArrayList<HashMap<String,String>> permissionData = new ArrayList<>();
                for (String emp:checkList){
                    HashMap<String,String> permissionDataMap = new HashMap<>();
                    permissionDataMap.put("permission_id",emp);
                    permissionData.add(permissionDataMap);
                }
                Gson json = new Gson();
                String permissionId= json.toJson(permissionData);
                Log.d(TAG, "onClick: data of day : "+permissionId);
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new UpdatePermission(new PrefManager(getContext())
                                .getVendorId(),stylistId,permissionId, apiCommunicator).getStringRequest());
            }
        });
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("update_permission")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
        }
        if (status.equalsIgnoreCase("get_permission_byId")) {
            List<GetPermissionData> list = new GsonBuilder().create().fromJson(response, GetPermissionList.class).getResult();
            permissionAdapter.setCheckedList(list);
        }
        if (status.equalsIgnoreCase("settingEmp_list")) {
            List<SettingEmpData> list = new GsonBuilder().create().fromJson(response, SettingEmpList.class).getResult();
            setAdapter(list);
            adapter.notifyDataSetChanged();
        }
        if (status.equalsIgnoreCase("permission_list")) {
            permissionDatalist = new GsonBuilder().create().fromJson(response, EmployeePermissionList.class).getResult();
            setAdapter1(permissionDatalist);
            permissionAdapter.notifyDataSetChanged();
            MySingleton.getInstance(getContext()).addToRequestQue(new GetPermissionById(apiCommunicator, new PrefManager(getContext()).getVendorId(), stylistId).getStringRequest());
        }
    }

    private void setAdapter(List<SettingEmpData> list) {
        adapter = new UserPermissionAdapter(getContext(), list, apiCommunicator,this);
        //   recyclerView.setAdapter(new CategoryListAdapter(getContext(), list, apiCommunicator));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setAdapter1(List<EmployeePermissionData> list) {
        permissionAdapter = new UserPermissionEmployeeAdapter(getContext(), list, apiCommunicator,this);
        permissionList.setAdapter(permissionAdapter);
        permissionAdapter.notifyDataSetChanged();
    }


    @Override
    public void EmployeeOne(View view, int position, String emp_id) {
        stylistId = emp_id;
     //   Toast.makeText(getActivity(), ""+emp_id, Toast.LENGTH_SHORT).show();
        MySingleton.getInstance(getContext()).addToRequestQue(new GetPermissionById(apiCommunicator, new PrefManager(getContext()).getVendorId(), stylistId).getStringRequest());
    }

    @Override
    public void EmployeePermissionMulti(View view, int position) {
          String s = permissionDatalist.get(position).getPermissionId();
          checkList.add(s);
    }
}
