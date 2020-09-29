package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetCustomers;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.CustomerAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomer;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomerData;
import com.hubwallet.apptspos.Utils.PrefManager;
import com.hubwallet.apptspos.customer.CustomerClickListener;
import com.hubwallet.apptspos.customer.CustomerFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerLIstFragment extends Fragment implements ApiCommunicator, CustomerClickListener {

    RecyclerView recyclerView;
    CustomerAdapter customerAdapter = null;
    ImageButton imageButton;
    Communicator communicator;
    ApiCommunicator apiCommunicator;
    List<GetCustomerData> list;
    EditText search;
    NavigationActivity navigationActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationActivity = (NavigationActivity) getActivity();
        communicator = (Communicator) getActivity();
        apiCommunicator = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.customer_list_fragment, null, false);
        ButterKnife.bind(this, v);

        recyclerView = v.findViewById(R.id.customerRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        search = v.findViewById(R.id.searchCustomerEditText);

        navigationActivity.onProgress();
        MySingleton.getInstance(getContext()).addToRequestQue(new GetCustomers(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (customerAdapter != null) {
                    customerAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @OnClick(R.id.btnAddCustomer)
    public void onAddCustomerClick(){

        NavigationActivity.fm.beginTransaction()
                .replace(R.id.containerMain, CustomerFragment.Companion.newInstance())
                .commit();
//        communicator.sendmessage("customer_edit", "");
    }

    @Override
    public void getApiData(String status, String response) {
        navigationActivity.offProgress();
        if (status.equalsIgnoreCase("get_customers")) {
            list = new GsonBuilder().create().fromJson(response, GetCustomer.class).getResult();
            setAdapter(list);
        }
        if (status.equalsIgnoreCase("edit_the_customer")) {
            communicator.sendmessage("customer_edit", response);
        }
        if (status.equalsIgnoreCase("delete_customer")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("customer_edit", "delete");
        }
    }

    private void setAdapter(List<GetCustomerData> list) {
        customerAdapter = new CustomerAdapter(getContext(), list, apiCommunicator,this);
        recyclerView.setAdapter(customerAdapter);
    }

    @Override
    public void onClickCustItem(@NotNull String customerId) {
        CustomerFragment fragment=CustomerFragment.Companion.newInstance();
        Bundle bundle=new Bundle();
        bundle.putString("customerId",customerId);
        fragment.setArguments(bundle);
        NavigationActivity.fm.beginTransaction()
                .replace(R.id.containerMain,fragment )
                .commit();
    }
}
