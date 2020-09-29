package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetStylists;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.StylistListAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistList;
import com.hubwallet.apptspos.Utils.PrefManager;
import com.hubwallet.apptspos.customer.CustomerClickListener;
import com.hubwallet.apptspos.employes.details.EmployesDetailsFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StylishFragment extends Fragment implements ApiCommunicator, CustomerClickListener {
    Communicator communicator;
    private RecyclerView recyclerView;
    private ApiCommunicator apiCommunicator;
    private EditText search;
    private StylistListAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stylish_list, null, false);

        recyclerView = view.findViewById(R.id.stylistRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        search = view.findViewById(R.id.searchstylistEditText);
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
        MySingleton.getInstance(getContext()).addToRequestQue(new GetStylists(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        Button button = view.findViewById(R.id.btnAddService);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* EmployesDetailsFragment fragment = EmployesDetailsFragment.Companion.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("stylistId", " ");
                fragment.setArguments(bundle);
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerEmpMenu, fragment).commit();*/
            }
        });
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_stylists")) {
            List<GetStylistData> list = new GsonBuilder().create().fromJson(response, GetStylistList.class).getResult();
            setAdapter(list);
        }
        if (status.equalsIgnoreCase("delete_stylist")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("stylist", "delete");
        }
        if (status.equalsIgnoreCase("stylist_by_id")) {
            communicator.sendmessage("stylist", response);
        }
    }


    private void setAdapter(List<GetStylistData> list) {
        adapter = new StylistListAdapter(getContext(), list, apiCommunicator, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClickCustItem(@NotNull String customerId) {
        EmployesDetailsFragment fragment = EmployesDetailsFragment.Companion.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("stylistId", customerId);
        fragment.setArguments(bundle);
        NavigationActivity.fm.beginTransaction()
                .replace(R.id.containerEmpMenu, fragment)
                .commit();
    }
}
