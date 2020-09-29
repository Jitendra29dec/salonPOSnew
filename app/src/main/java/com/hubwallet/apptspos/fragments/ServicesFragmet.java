package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddService;
import com.hubwallet.apptspos.APis.EditService;
import com.hubwallet.apptspos.APis.GetServiceByID;
import com.hubwallet.apptspos.APis.GetServiceCategoryApi;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetServiceByIDModel.GetServicesByData;
import com.hubwallet.apptspos.Utils.Models.GetServiceByIDModel.GetServicesByList;
import com.hubwallet.apptspos.Utils.Models.ServiesCategory.ServicesCategory;
import com.hubwallet.apptspos.Utils.Models.ServiesCategory.ServicesCategoryData;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ServicesFragmet extends Fragment implements ApiCommunicator {
    ApiCommunicator apiCommunicator;
    Spinner category;
    EditText name, price, sku, duration;
    Button add, cancel;
    boolean isempt = false;
    Communicator communicator;
    String id;
    boolean isforEdit = false;
    boolean isFirstTime = false;
    ProgressBar progressBar;
    private List<ServicesCategoryData> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        if (getArguments() != null) {
            id = getArguments().getString("service_id");
            isforEdit = true;
            isFirstTime = true;
            NavigationActivity.isInProgress = true;
            MySingleton.getInstance(getContext()).addToRequestQue(new GetServiceCategoryApi(apiCommunicator).getStringRequest());
        } else {
            MySingleton.getInstance(getContext()).addToRequestQue(new GetServiceCategoryApi(apiCommunicator).getStringRequest());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, null, false);
        category = view.findViewById(R.id.categorySpinner);
        name = view.findViewById(R.id.serviceNameEditText);
        price = view.findViewById(R.id.servicePriceEditText);
        sku = view.findViewById(R.id.skuEditText);
        duration = view.findViewById(R.id.durationEditText);
        add = view.findViewById(R.id.addServiceButton);
        cancel = view.findViewById(R.id.cancelServiceButton);
        progressBar = view.findViewById(R.id.progressBarService);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.sendmessage("services", "");
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(name);
                check(price);
                check(duration);
                if (!isempt) {
                    int pos = category.getSelectedItemPosition();
                    if (!isforEdit) {
                        MySingleton.getInstance(getContext()).addToRequestQue(new AddService(new PrefManager(getContext()).getVendorId(), "",list.get(pos).getCategoryId(), name.getText().toString(),  price.getText().toString(), duration.getText().toString(),"","","", apiCommunicator).getStringRequest());
                    } else {
                        MySingleton.getInstance(getContext()).addToRequestQue(new EditService("",id, list.get(pos).getCategoryId(),"", name.getText().toString(), price.getText().toString(), duration.getText().toString(),"", "","",apiCommunicator).getStringRequest());
                    }
                }
            }
        });
        return view;
    }

    private void check(EditText name) {
        if (name.getText().toString().isEmpty()) {
            setError(name);
            isempt = true;
        }
    }

    private void setError(EditText name) {
        name.setError("Field cant be empty");
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("services_category")) {
            list = new GsonBuilder().create().fromJson(response, ServicesCategory.class).getResult();
            List<String> list1 = new ArrayList<>();
            for (ServicesCategoryData data : list) {
                list1.add(data.getCategoryName());
            }
            category.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list1));
            if (isforEdit) {
                MySingleton.getInstance(getContext()).addToRequestQue(new GetServiceByID("",id, apiCommunicator).getStringRequest());
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }

        if (status.equalsIgnoreCase("service_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("services", "");
        }
        if (status.equalsIgnoreCase("service_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("services", "");
        }
        if (status.equalsIgnoreCase("get_service_by_id")) {
            List<GetServicesByData> data = new GsonBuilder().create().fromJson(response, GetServicesByList.class).getResult();
            update(data.get(0));

        }
    }

    private void update(GetServicesByData data) {
        progressBar.setVisibility(View.GONE);
        if (data.getCategoryName() != null) {
            name.setText(data.getServiceName());
        }
       /* if (data.getPrice() != null) {
            price.setText(data.getPrice());
        }

        if (data.getDuration() != null) {
            duration.setText(data.getDuration());
        }*/
        if (data.getCategoryName() != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCategoryName().equalsIgnoreCase(data.getCategoryName())) {
                    category.setSelection(i);
                }
            }
        }
        add.setText("Edit");

        NavigationActivity.isInProgress = false;
    }
}
