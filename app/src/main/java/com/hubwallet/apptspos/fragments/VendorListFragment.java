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
import com.hubwallet.apptspos.APis.GetVendor;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.VendorListAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VendorListFragment extends Fragment implements ApiCommunicator {

    @BindView(R.id.recyclerViewListItem)
    RecyclerView recyclerView;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    Button btnAddVendor;
    EditText searchVendorEditText;
    private VendorListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_vendor_list, null, false);
        ButterKnife.bind(this, v);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        btnAddVendor = v.findViewById(R.id.btnAddVendor);
        searchVendorEditText = v.findViewById(R.id.searchVendorEditText);
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetVendor(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);

        searchVendorEditText.addTextChangedListener(new TextWatcher() {
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

        btnAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.productMainSubMenu, new AddVendorFragment())
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_supplier")) {
            List<SupplierData> list = new GsonBuilder().create().fromJson(response, SupplierList.class).getMessage();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("supplier_id")) {
            communicator.sendmessage("vendor_add", response);
        }

    }

    private void setAdapter(List<SupplierData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new VendorListAdapter(getContext(),list,apiCommunicator);
        recyclerView.setAdapter(adapter);
    }
}
