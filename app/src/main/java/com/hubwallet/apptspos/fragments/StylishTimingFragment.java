package com.hubwallet.apptspos.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddStylist;
import com.hubwallet.apptspos.APis.EditStylist;
import com.hubwallet.apptspos.APis.GetCountery;
import com.hubwallet.apptspos.APis.GetServices;
import com.hubwallet.apptspos.APis.GetState;
import com.hubwallet.apptspos.APis.GetStylistByID;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.AddStylistInfoAdapter;
import com.hubwallet.apptspos.Adapters.StylistTimmingAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryData;
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryList;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesList;
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateData;
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateList;
import com.hubwallet.apptspos.Utils.Models.GetStylistByIdModel.GetStylistById;
import com.hubwallet.apptspos.Utils.Models.GetStylistByIdModel.Result;
import com.hubwallet.apptspos.Utils.Models.GetStylistByIdModel.Schedule;
import com.hubwallet.apptspos.Utils.Models.GetStylistByIdModel.Service;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class StylishTimingFragment extends Fragment implements ApiCommunicator {
    private RecyclerView recyclerView, timmingView;
    private ApiCommunicator apiCommunicator;
    private Spinner countrySpinner, stateSpinner, stylistType;
    private List<CountryData> Countries;
    private List<GetStateData> statelist;
    private EditText nameFelid, lastNameField, nickNameField, emaiField, phoneField, alternatePhoneField, cityField, postalField, addressField;
    private Button addStylist, addImage;
    private int pick = 101;
    private String imageString;
    private String imagePAth;
    private boolean isPicRequired = false;
    private AddStylistInfoAdapter stylistServicesAdapter;
    private StylistTimmingAdapter weeklySchedule;
    private ProgressBar progressBar;
    private Communicator communicator;
    private boolean isForEdit = false;
    private String stylist_id;
    private Result result;
    private List<GetServicesData> servicesList;
    private boolean isloading = false;
    private boolean isFirstTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiCommunicator = this;
        if (getArguments() != null) {
            Bundle b = getArguments();
            String id = getArguments().getString("stylist_id", "");
            if (!id.isEmpty()) {
                NavigationActivity.isInProgress = true;
                stylist_id = id;
                isForEdit = true;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stylisth_details, null, false);
        initialize(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timmingView = view.findViewById(R.id.recyclerViewStylistTiming);
        timmingView.setLayoutManager(new LinearLayoutManager(getContext()));
        weeklySchedule = new StylistTimmingAdapter(getContext(), null);
        timmingView.setAdapter(weeklySchedule);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetServices(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        MySingleton.getInstance(getContext()).addToRequestQue(new GetCountery(apiCommunicator).getStringRequest());
        if (isForEdit) {
            addStylist.setText("Update");
            progressBar.setVisibility(View.VISIBLE);
            isFirstTime = true;
        }
        return view;
    }

    private void initialize(View view) {
        communicator = (Communicator) getActivity();
        progressBar = view.findViewById(R.id.progressBarStylist);
        recyclerView = view.findViewById(R.id.recyclerViewStylistDetail);
        countrySpinner = view.findViewById(R.id.countrySpinner);
        stylistType = view.findViewById(R.id.stylistTypeSpinner);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        nameFelid = view.findViewById(R.id.firstNameStylist);
        lastNameField = view.findViewById(R.id.lastNameStylist);
        nickNameField = view.findViewById(R.id.nickNameStylist);
        emaiField = view.findViewById(R.id.emailStylist);
        phoneField = view.findViewById(R.id.phoneStylist);
        alternatePhoneField = view.findViewById(R.id.alternatePhoneStylist);
        cityField = view.findViewById(R.id.cityStylist);
        postalField = view.findViewById(R.id.postalStylist);
        addressField = view.findViewById(R.id.addressStylist);
        addImage = view.findViewById(R.id.addImageButton);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageStylist();
            }
        });
        addStylist = view.findViewById(R.id.addStylistButton);
        addStylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStylistFunction();
            }
        });
    }

    private void addStylistFunction() {
        //  Log.e("val", new PrefManager(getContext()).getVendorId());
        String stylistTypeValue = "0";
        String firstName, lastName, nickName, postal, address, phone, alternatePhone, email, countryId = "", photo, stateId, city;
        if (stylistType.getSelectedItemPosition() == 0) {
            stylistTypeValue = "1";
        } else {
            stylistTypeValue = "2";
        }
        if (!nameFelid.getText().toString().isEmpty()) {
            firstName = nameFelid.getText().toString();
        } else {
            sendError(nameFelid);
            return;
        }
        if (!lastNameField.getText().toString().isEmpty()) {
            lastName = lastNameField.getText().toString();
        } else {
            sendError(lastNameField);
            return;
        }
        if (!nickNameField.getText().toString().isEmpty()) {
            nickName = nickNameField.getText().toString();

        } else {
            sendError(nickNameField);
            return;
        }
        if (!emaiField.getText().toString().isEmpty()) {
            email = emaiField.getText().toString();
        } else {
            sendError(emaiField);
            return;
        }
        if (!phoneField.getText().toString().isEmpty()) {
            phone = phoneField.getText().toString();

        } else {
            sendError(phoneField);
            return;
        }
        if (!alternatePhoneField.getText().toString().isEmpty()) {
            alternatePhone = alternatePhoneField.getText().toString();

        } else {
            sendError(alternatePhoneField);
            return;
        }
        if (!cityField.getText().toString().isEmpty()) {
            city = cityField.getText().toString();

        } else {
            sendError(cityField);
            return;
        }
        if (!postalField.getText().toString().isEmpty()) {
            postal = postalField.getText().toString();
        } else {
            sendError(postalField);
            return;
        }
        if (!addressField.getText().toString().isEmpty()) {
            address = addressField.getText().toString();

        } else {
            sendError(addressField);
            return;
        }
        if (!isPicRequired) {
            imageString = "";
        }
        int countryPos = countrySpinner.getSelectedItemPosition();
        String id = Countries.get(countryPos).getCountryD();
        if (!id.isEmpty()) {
            countryId = id;
        }
        int statePos = stateSpinner.getSelectedItemPosition();
        stateId = statelist.get(statePos).getStateId();
        if (stateId.isEmpty()) {
            return;
        }
        String servicesStylist = stylistServicesAdapter.getStylistSchedule();
        String stylistScedule = weeklySchedule.getWeekSchedule();
        if (isForEdit) {
            MySingleton.getInstance(getContext()).addToRequestQue(new EditStylist(new PrefManager(getContext()).getVendorId(), imageString, stylistTypeValue, firstName, lastName, nickName, phone, alternatePhone, countryId, stateId, postal, address, city, servicesStylist, stylistScedule, email, stylist_id, apiCommunicator).getStringRequest());
            progressBar.setVisibility(View.VISIBLE);
        } else {
            MySingleton.getInstance(getContext()).addToRequestQue(new AddStylist(new PrefManager(getContext()).getVendorId(), imageString, stylistTypeValue, firstName, lastName, nickName, phone, alternatePhone, countryId, stateId, postal, address, city, servicesStylist, stylistScedule, email, apiCommunicator).getStringRequest());
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void sendError(EditText nameFelid) {
        nameFelid.setError("Field Can`t be empty");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pick && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                isPicRequired = true;//if get path thn set its true.
                addImage.setText("Image Selected");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] byteArray = stream.toByteArray();
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                progressBar.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Error Occurred please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    private void addImageStylist() {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), pick);

    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("services_list")) {
            servicesList = new GsonBuilder().create().fromJson(response, GetServicesList.class).getResult();
            stylistServicesAdapter = new AddStylistInfoAdapter(getContext(), servicesList);
            recyclerView.setAdapter(stylistServicesAdapter);
        }
        if (status.equalsIgnoreCase("stylist_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("stylist", "");
            progressBar.setVisibility(View.GONE);

        }
        if (status.equalsIgnoreCase("stylist_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("stylist", "");
            progressBar.setVisibility(View.GONE);

        }
        if (status.equalsIgnoreCase("get_stylist_by_id")) {
            GetStylistById data = new GsonBuilder().create().fromJson(response, GetStylistById.class);
            update(data);

        }
        if (status.equalsIgnoreCase("state_list")) {
            statelist = new GsonBuilder().create().fromJson(response, GetStateList.class).getResult();
            ArrayList<String> list = new ArrayList<>();
            for (GetStateData d : statelist) {
                list.add(d.getStateName());
            }
            if (getContext() != null) {
                stateSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list));
                if (isForEdit && isFirstTime) {// loading state if it is open for edit purpose
                    MySingleton.getInstance(getContext()).addToRequestQue(new GetStylistByID(stylist_id, new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());
                }
            }
        }
        if (status.equalsIgnoreCase("country_list")) {
            Log.e("getApiData: ", response);
            Countries = new GsonBuilder().create().fromJson(response, CountryList.class).getResult();
            final ArrayList<String> countryList = new ArrayList<>();
            if (Countries != null) {
                for (CountryData data : Countries) {
                    countryList.add(data.getCountryName());
                }
            }
            if (countryList != null) {
                countrySpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, countryList));
                countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MySingleton.getInstance(getContext()).addToRequestQue(new GetState(apiCommunicator, Countries.get(position).getCountryD()).getStringRequest());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }

    }

    private void update(GetStylistById data) {
        result = data.getResult();
        //  Log.e( "update: ",data.toString() );
        if (result.getPhone() != null) {
            phoneField.setText(result.getPhone());
        }
        if (result.getStylistType().equalsIgnoreCase("1")) {
            stylistType.setSelection(0);
        } else {
            stylistType.setSelection(1);
        }
        if (result.getFirstname() != null) {
            nameFelid.setText(result.getFirstname());
        }
        if (result.getLastname() != null) {
            lastNameField.setText(result.getLastname());
        }
        if (result.getAddress() != null) {
            addressField.setText(result.getAddress());
        }
        if (result.getAlternatePhone() != null) {
            alternatePhoneField.setText(result.getAlternatePhone());
        }
        if (result.getCity() != null) {
            cityField.setText(result.getCity());
        }
        if (result.getNickname() != null) {
            nickNameField.setText(result.getNickname());
        }
        if (result.getPhone() != null) {
            phoneField.setText(result.getPhone());
        }
        if (result.getEmail() != null) {
            emaiField.setText(result.getEmail());
        }
        if (result.getPostalCode() != null) {
            postalField.setText(result.getPostalCode());
        }
        if (result.getCountryId() != null) {
            for (int i = 0; i < Countries.size(); i++) {
                CountryData c = Countries.get(i);
                if (c.getCountryD().equalsIgnoreCase(result.getCountryId())) {
                    countrySpinner.setSelection(i);
                }
            }
        }
        if (result.getStateId() != null) {
            for (int i = 0; i < statelist.size(); i++) {
                if (statelist.get(i).getStateId().equalsIgnoreCase(result.getStateId())) {
                    stateSpinner.setSelection(i);
                }
            }
        }
        List<Service> services = data.getService();
        for (int i = 0; i < services.size(); i++) {
            for (int j = 0; j < servicesList.size(); j++) {
                if (servicesList.get(j).getServiceID().equalsIgnoreCase(services.get(i).getServiceId())) {
                    servicesList.get(j).setChecked(true);
                    servicesList.get(j).setDuration(services.get(i).getDuration());
                    servicesList.get(j).setPrice(services.get(i).getPrice());
                    servicesList.get(j).setServiceName(services.get(i).getServiceName());
                }
            }
        }
        stylistServicesAdapter = new AddStylistInfoAdapter(getContext(), servicesList);
        recyclerView.setAdapter(stylistServicesAdapter);
        List<Schedule> schedules = data.getSchedule();
        if (data.getSchedule() == null || data.getSchedule().size() == 0) {
            weeklySchedule = new StylistTimmingAdapter(getContext(), null);
        } else {
            weeklySchedule = new StylistTimmingAdapter(getContext(), schedules);
        }
        timmingView.setAdapter(weeklySchedule);
        NavigationActivity.isInProgress = false;
        isFirstTime = false;//so than dont load stylist again
        progressBar.setVisibility(View.GONE);
    }
}
