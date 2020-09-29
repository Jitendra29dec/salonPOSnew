package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddCategory;
import com.hubwallet.apptspos.APis.AddVendor;
import com.hubwallet.apptspos.APis.EditVendor;
import com.hubwallet.apptspos.APis.GetCategoryById;
import com.hubwallet.apptspos.APis.GetStateList;
import com.hubwallet.apptspos.APis.GetVendor;
import com.hubwallet.apptspos.APis.GetVendorById;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.BrandModel.GetBrand;
import com.hubwallet.apptspos.Utils.Models.GetCategoryByData;
import com.hubwallet.apptspos.Utils.Models.GetServiceByIDModel.GetServicesByData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierByIdData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierData;
import com.hubwallet.apptspos.Utils.Models.StateData;
import com.hubwallet.apptspos.Utils.Models.StateList;
import com.hubwallet.apptspos.Utils.PrefManager;
import com.hubwallet.apptspos.employes.details.state.StateDetails;
import com.hubwallet.apptspos.employes.details.state.StateListRes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AddVendorFragment extends Fragment implements ApiCommunicator, View.OnClickListener {
    private AppCompatTextView submitBtn, backBtn, vendorTitle;
    private EditText name, email, phone, city, zipcode, address;
    private Spinner stateSpinner;
    private Communicator communicator;
    ApiCommunicator apiCommunicator;
    private boolean isForEdit = false;
    private String supplierId = "";
    private List<StateData> stateList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            supplierId = getArguments().getString("supplier_id", "");
            if (!supplierId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", supplierId);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vendor, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        submitBtn = view.findViewById(R.id.submitButton);
        name = view.findViewById(R.id.nameEdiText);
        email = view.findViewById(R.id.emailEdiText);
        phone = view.findViewById(R.id.phoneEdiText);
        zipcode = view.findViewById(R.id.zipcodeEdiText);
        city = view.findViewById(R.id.cityEditText);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        address = view.findViewById(R.id.addressEditText);
        vendorTitle = view.findViewById(R.id.vendorTitle);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetStateList(apiCommunicator, "231").getStringRequest());
        backBtn = view.findViewById(R.id.imgBack);
        // submitBtn.setOnClickListener(this);

        if (isForEdit) {
            vendorTitle.setText("Edit Vendor");
            submitBtn.setText("Update");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.productMainSubMenu, new VendorListFragment())
                        .commit();
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String staeId;
                staeId = stateList.get(stateSpinner.getSelectedItemPosition() != 0 ? stateSpinner.getSelectedItemPosition() - 1 : stateSpinner.getSelectedItemPosition()).getStateId();
                String isValidEmail = email.getText().toString();
                if (!name.getText().toString().isEmpty() && name.getText().toString().length() > 0) {
                    if (!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(isValidEmail).matches()) {
                        if (!phone.getText().toString().isEmpty() && phone.getText().toString().length() > 0) {
                            if (!zipcode.getText().toString().isEmpty() && zipcode.getText().toString().length() > 0) {
                                if (!city.getText().toString().isEmpty() && city.getText().toString().length() > 0) {
                                    ((NavigationActivity) getActivity()).onProgress();
                                    if (isForEdit) {
                                        MySingleton.getInstance(getContext())
                                                .addToRequestQue(new EditVendor(new PrefManager(getContext())
                                                        .getVendorId(), supplierId, name.getText().toString(), email.getText().toString(), phone.getText().toString(),
                                                        staeId, city.getText().toString(),
                                                        zipcode.getText().toString(), address.getText().toString(), apiCommunicator).getStringRequest());
                                    } else {
                                        MySingleton.getInstance(getContext())
                                                .addToRequestQue(new AddVendor(new PrefManager(getContext())
                                                        .getVendorId(), name.getText().toString(), email.getText().toString(), phone.getText().toString(),
                                                        staeId, city.getText().toString(),
                                                        zipcode.getText().toString(), address.getText().toString(), apiCommunicator).getStringRequest());
                                    }
                                } else {
                                    city.setError("Enter City");
                                }
                            } else {
                                zipcode.setError("Enter Zicode");
                            }
                        } else {
                            phone.setError("Enter phone");
                        }
                    }else {
                        email.setError("Enter valid Email");
                    }
                }else {
                    name.setError("Enter name");
                }
              /*  MySingleton.getInstance(getContext())
                        .addToRequestQue(new AddVendor(new PrefManager(getContext())
                                .getVendorId(), name.getText().toString(),email.getText().toString(), phone.getText().toString(),
                                staeId, city.getText().toString(),
                                zipcode.getText().toString(), address.getText().toString(), apiCommunicator).getStringRequest());*/
            }
        });
        if (isForEdit) {
            MySingleton.getInstance(getContext()).addToRequestQue(new GetVendorById(supplierId, new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());
        }

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_state")) {
            stateList = new GsonBuilder().create().fromJson(response, StateList.class).getResult();
            ArrayList<String> list = new ArrayList<>();
            list.add("SELECT");
            for (StateData b : stateList) {
                list.add(b.getStateName());
            }
            setSpinnerBrand(list, stateSpinner);
        }
        if (status.equalsIgnoreCase("vendorAdd")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("vendor_add", "");
        }

        if (status.equalsIgnoreCase("vendor_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("vendor_add", "");
        }
        if (status.equalsIgnoreCase("get_vendor_by_id")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                SupplierByIdData supplierByIdData = new GsonBuilder().create().fromJson(jsonObject.getString("message"), SupplierByIdData.class);
                updatate(supplierByIdData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitButton:
                //     addCategory();
                break;
        }
    }

    private void addCategory() {

    }

    private String getString(EditText name) {
        String val = name.getText().toString();
        return val;
    }


    private void updatate(SupplierByIdData supplierByIdData) {
        name.setText(supplierByIdData.getSupplierName());
        email.setText(supplierByIdData.getEmail());
        zipcode.setText(supplierByIdData.getPincode());
        phone.setText(supplierByIdData.getPhone());
        address.setText(supplierByIdData.getAddress());
        city.setText(supplierByIdData.getCity());

        if (stateList != null) {
            for (int i = 0; i < stateList.size(); i++) {
                if (stateList.get(i).getStateId().equalsIgnoreCase(supplierByIdData.getState_id())) {
                    stateSpinner.setSelection(i + 1);
                }
            }
        }

       /* if (product.getBusinessUse().equalsIgnoreCase("1")) {
            //   useForBusinessCheckBok.setChecked(true);
        }*/
    }

    private void setSpinnerBrand(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }
}
