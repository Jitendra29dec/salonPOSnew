package com.hubwallet.apptspos.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddCustomerPopup;
import com.hubwallet.apptspos.APis.AddGroupAppointmnet;
import com.hubwallet.apptspos.APis.EditGroupAppointmnet;
import com.hubwallet.apptspos.APis.GetCustomers;
import com.hubwallet.apptspos.APis.GetServices;
import com.hubwallet.apptspos.APis.GetStylists;
import com.hubwallet.apptspos.APis.GroupAppointmentDetailApi;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Adapters.GroupAppointmentAdapter;
import com.hubwallet.apptspos.Adapters.GroupServiceAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Constants;
import com.hubwallet.apptspos.Utils.CustomProgress;
import com.hubwallet.apptspos.Utils.GroupAppointmentInterface;
import com.hubwallet.apptspos.Utils.LogUtils;
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryData;
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryList;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomer;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomerData;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesList;
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateData;
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateList;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistList;
import com.hubwallet.apptspos.Utils.Models.GroupAppointment.GroupAppointmentData;
import com.hubwallet.apptspos.Utils.Models.GroupAppointment.GroupAppointmentList;
import com.hubwallet.apptspos.Utils.Models.GroupAppointmentModel;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GroupAppointmentActivity extends AppCompatActivity
        implements ApiCommunicator, GroupAppointmentInterface {

    private static final String[] genderArr = new String[]{"Male", "Female"};
    private static final String[] preferredContactArr = new String[]{"Mobile", "Home", "Work"};

    private AutoCompleteTextView autoCompleteTextView;
    private List<GetCustomerData> customerList;
    private TextView addButton;
    private Dialog createCustomerDialog;
    private ApiCommunicator apiCommunicator;
    private String vendorID = "";
    private RecyclerView recyclerView, serviceRecyclerView;
    private GroupAppointmentAdapter appointmentAdapter;
    private List<GetCustomerData> list;
    private GroupAppointmentInterface groupAppointmentInterface;
    private String time, date,notEdit;
    private List<GetStylistData> stylistList = null;
    private List<GetServicesData> servicList = null;
    private TextView selectedCustomer;
    private List<List<GroupAppointmentModel>> appointmentList;
    private GroupServiceAdapter groupServiceAdapter;
    private Button bookingButton;
    private String AppointmentNote = "";
    private String EventsNote = "";
    private String EventsName = "";
    private Button appointmentNoteButton;
    private Button btnAddEvent;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private ArrayList<String> appointmentDates = new ArrayList<>(), appointmentTimes = new ArrayList<>(), stylistId = new ArrayList<>(), serviceID = new ArrayList<>(), customerId = new ArrayList<>(), duration = new ArrayList<>();
    private String tokenNumber;
    private CustomProgress customProgress;
    private Activity mActivity;
    private List<CountryData> countryDataList;
    private List<GetStateData> stateList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_appointment);
        Init();
        mActivity = GroupAppointmentActivity.this;
        //  Log.e(TAG, "onCreate: ", );
        if (getIntent().getExtras() != null) {
            tokenNumber = getIntent().getStringExtra("token_number");
        } else {
            tokenNumber = "0";
        }
        customProgress = new CustomProgress(mActivity);
        autoCompleteTextView = findViewById(R.id.searchViewGroupActivity);
        TextView txtAppointmentDate = findViewById(R.id.txtAppointmentDate);
       // serviceRecyclerView.setVisibility(View.GONE);
        appointmentAdapter = new GroupAppointmentAdapter(mActivity, list, groupAppointmentInterface);
        recyclerView.setAdapter(appointmentAdapter);
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");
        notEdit = getIntent().getStringExtra("notedit");
        txtAppointmentDate.setText(String.format("Appointment Date : %s", date));
        //  Log.e( "onCreate: ",time +date );
        appointmentList = new ArrayList<>();
        //groupServiceAdapter = new GroupServiceAdapter(0, appointmentList, stylistList, servicList);
        //serviceRecyclerView.setAdapter(groupServiceAdapter);
        apiCommunicator = this;
        progressBar = findViewById(R.id.groupActivityProgress);
        findViewById(R.id.activityCloseButton).setOnClickListener(v -> finish());
        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());



        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (GetCustomerData data : customerList) {
                    if (data.getCustomerName().equalsIgnoreCase(parent.getItemAtPosition(position) + "")) {
                        if (appointmentAdapter.getItemCount() == 0) {//if first element
                            data.setSelected(true);
                        }
                        appointmentAdapter.updateList(data);
                        autoCompleteTextView.setText("");
                        if (appointmentAdapter.getItemCount() > 0) {
                            serviceRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

        vendorID = new PrefManager(mActivity).getVendorId();

        addButton.setOnClickListener(v -> createDialog());

        appointmentNoteButton.setOnClickListener(v -> {
            final EditText noteView;
            Button submit, cancel;
            ImageView imgClose;
            TextView head;
            final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
            View view = LayoutInflater.from(mActivity).inflate(R.layout.note_view_appointment_group, null, false);
            dialog.setView(view);
            noteView = view.findViewById(R.id.noteEditTextGroupAlert);
            submit = view.findViewById(R.id.submitButtonGroupAlert);
            cancel = view.findViewById(R.id.btnCancel);
            imgClose = view.findViewById(R.id.imgClose);
            if (AppointmentNote != null && !AppointmentNote.isEmpty()) {
                noteView.setText(AppointmentNote);
            }
            cancel.setOnClickListener(view13 -> dialog.dismiss());
            imgClose.setOnClickListener(view14 -> dialog.dismiss());
            submit.setOnClickListener(v12 -> {
                AppointmentNote = noteView.getText().toString();
                dialog.dismiss();
                Toast.makeText(mActivity, "Appointment Note Saved", Toast.LENGTH_SHORT).show();
            });
            dialog.show();
        });

        btnAddEvent.setOnClickListener(v -> {
            final EditText ETeventsnotes, ETeventname;
            Button BTsubmintevents, cancel;
            ImageView imgClose;
           // TextView head;
            final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
            View view = LayoutInflater.from(mActivity).inflate(R.layout.event_view_appointment_group, null, false);
            dialog.setView(view);
            ETeventname = view.findViewById(R.id.ETeventname);
            ETeventsnotes = view.findViewById(R.id.ETeventsnotes);
            BTsubmintevents = view.findViewById(R.id.BTsubmintevents);
            cancel = view.findViewById(R.id.btnCancel);
            imgClose = view.findViewById(R.id.imgClose);
            if (EventsNote != null && !EventsNote.isEmpty() && EventsName != null && !EventsName.isEmpty()) {
                ETeventsnotes.setText(EventsNote);
                ETeventname.setText(EventsName);
            }
            cancel.setOnClickListener(view1 -> dialog.dismiss());

            imgClose.setOnClickListener(view12 -> dialog.dismiss());


            BTsubmintevents.setOnClickListener(v1 -> {
                EventsNote = ETeventsnotes.getText().toString();
                EventsName = ETeventname.getText().toString();
                dialog.dismiss();
                Toast.makeText(mActivity, "Appointment Note Saved", Toast.LENGTH_SHORT).show();
            });
            dialog.show();
        });


        bookingButton.setOnClickListener(v -> {
            List<String> appointmentIds = new ArrayList<>();//in case of editing groupApp
            boolean isCaptainSelected = false;
            String captain = "0";
            for (GetCustomerData x : appointmentAdapter.getCustomerlist()) {
                if (x.isCaptain()) {
                    captain = x.getCustomerId();
                    isCaptainSelected = true;
                }
            }
            if (!isCaptainSelected) {
                Toast.makeText(mActivity, "Select captain first!", Toast.LENGTH_SHORT).show();
                return;
            }
            for (List<GroupAppointmentModel> x : appointmentList) {
                for (GroupAppointmentModel model : x) {
                    if (model.getService().equalsIgnoreCase("") || model.getStylist().equalsIgnoreCase("")) {
                        Toast.makeText(mActivity, "Make sure to select service and stylists of all customer! ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            for (int i = 0; i < appointmentAdapter.getCustomerlist().size(); i++) {//customer list
                for (int j = 0; j < appointmentList.get(i).size(); j++) {
                    customerId.add(appointmentAdapter.getCustomer(i).getCustomerId());
                    duration.add(appointmentList.get(i).get(j).getDuration());
                    serviceID.add(appointmentList.get(i).get(j).getService());
                    stylistId.add(appointmentList.get(i).get(j).getStylist());
                    appointmentDates.add(appointmentList.get(i).get(j).getDate());
                    appointmentTimes.add(convertTo24HOUR(appointmentList.get(i).get(j).getTime()));
                    if (appointmentList.get(i).get(j).getAppointmentId() != null) {
                        appointmentIds.add(appointmentList.get(i).get(j).getAppointmentId());
                    } else {
                        appointmentIds.add("0");
                    }
                }
            }
            if (tokenNumber != null && !tokenNumber.equalsIgnoreCase("0")) {
                MySingleton.getInstance(mActivity).addToRequestQue(new EditGroupAppointmnet(new PrefManager(mActivity).getVendorId(), replaceBr(appointmentDates.toString()), replaceBr(serviceID.toString()), replaceBr(stylistId.toString()), replaceBr(duration.toString()), AppointmentNote, captain, replaceBr(customerId.toString()), replaceBr(appointmentIds.toString()), tokenNumber, replaceBr(appointmentTimes.toString()), apiCommunicator).getStringRequest());
            } else {
                MySingleton.getInstance(mActivity).addToRequestQue(new AddGroupAppointmnet(new PrefManager(mActivity).getVendorId(), getIntent().getStringExtra("id"), replaceBr(appointmentDates.toString()), replaceBr(serviceID.toString()), replaceBr(stylistId.toString()), replaceBr(duration.toString()), AppointmentNote, captain, replaceBr(customerId.toString()), replaceBr(appointmentTimes.toString()), apiCommunicator).getStringRequest());

            }
            customerId = null;
            customerId = new ArrayList<>();
            duration = null;
            duration = new ArrayList<>();
            serviceID = null;
            serviceID = new ArrayList<>();
            stylistId = null;
            stylistId = new ArrayList<>();
            appointmentDates = null;
            appointmentDates = new ArrayList<>();
            appointmentTimes = null;
            appointmentTimes = new ArrayList<>();
        });
    }

    public String convertTo24HOUR(String a) {

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
        final SimpleDateFormat output = new SimpleDateFormat("HH:mm", Locale.US);

        String timwe24hour = null;

        try {
            Date date = simpleDateFormat.parse(a);
            assert date != null;
            timwe24hour = output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timwe24hour;
    }

    private void Init() {
        recyclerView = findViewById(R.id.groupAppointmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        addButton = findViewById(R.id.addDialogButton);
        list = new ArrayList<>();
        serviceRecyclerView = findViewById(R.id.recyclerViewServicesGroup);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        groupAppointmentInterface = this;
        selectedCustomer = findViewById(R.id.selectedCustomerName);
        bookingButton = findViewById(R.id.groupAppointmentBookButton);
        appointmentNoteButton = findViewById(R.id.groupAppointmentNoteButton);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        linearLayout = findViewById(R.id.linearLayoutGroupAppointmentActivity);
    }

    private void createDialog() {

        @SuppressLint("InflateParams") View v = LayoutInflater.from(mActivity).inflate(R.layout.add_new_customer, null, false);
        createCustomerDialog = new Dialog(mActivity);
        createCustomerDialog.setContentView(v);
        final EditText firstname, lastName, email, mobile,
                editCCAppointmentEmail, editHomePhone, editReferredBy,
                editCity, editWorkPhone, editAddress;
        Spinner spinCountry, spinState, spinGender, spinPreferredContact;
        CheckBox checkSMS, checkEmail;
        Button cancelButton, addCustomerButton;
        firstname = v.findViewById(R.id.firstNameField);
        lastName = v.findViewById(R.id.lastNameField);
        email = v.findViewById(R.id.emailField);
        mobile = v.findViewById(R.id.editMobileNo);
        editCCAppointmentEmail = v.findViewById(R.id.editCCAppointmentEmail);
        editHomePhone = v.findViewById(R.id.editHomePhone);
        editReferredBy = v.findViewById(R.id.editReferredBy);
        editCity = v.findViewById(R.id.editCity);
        editWorkPhone = v.findViewById(R.id.editWorkPhone);
        editAddress = v.findViewById(R.id.editAddress);
        spinCountry = v.findViewById(R.id.spinCountry);
        spinState = v.findViewById(R.id.spinState);
        spinGender = v.findViewById(R.id.spinGender);
        spinPreferredContact = v.findViewById(R.id.spinPreferredContact);
        checkSMS = v.findViewById(R.id.checkSMS);
        checkEmail = v.findViewById(R.id.checkEmail);
        cancelButton = v.findViewById(R.id.cancelButton);
        addCustomerButton = v.findViewById(R.id.addCustomerBUtton);

        cancelButton.setOnClickListener(v1 -> createCustomerDialog.dismiss());

        spinGender.setAdapter(new ArrayAdapter<>(mActivity, R.layout.li_spin_search, genderArr));
        spinPreferredContact.setAdapter(new ArrayAdapter<>(mActivity, R.layout.li_spin_search, preferredContactArr));

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = firstname.getText().toString();
                String lname = lastName.getText().toString();
                String ema = email.getText().toString();
                String mob = mobile.getText().toString();

                if (fname.isEmpty()) {
                    sendError(firstname);
                 //   return;
                } else if (lname.isEmpty()) {
                    sendError(lastName);
                //    return;
                } else if (ema.isEmpty()) {
                    sendError(email);
                //    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(ema).matches()) {
                    sendValidError(email);
                  // return;
                } else if (editCCAppointmentEmail.getText().toString().length() > 0
                        && !Patterns.EMAIL_ADDRESS.matcher(editCCAppointmentEmail.getText().toString()).matches()) {
                    sendValidError(editCCAppointmentEmail);
                  //  return;
                } else if (mob.isEmpty()) {
                    sendError(mobile);
                 //   return;
                } else if (mob.length() != 10) {
                    sendValidError(mobile);
                 //   return;
                } else if (!checkSMS.isChecked() && !checkEmail.isChecked()) {
                    sendError(checkSMS);
                    sendError(checkEmail);
                  //  return;
                } else {
//                    MySingleton.getInstance(mActivity)
//                            .addToRequestQue(new AddCustomer(fname, lname, ema, mob, "", "", "", "", "", vendorID, "", apiCommunicator)
//                                    .getStringRequest());
                    customProgress.show();
                    MySingleton.getInstance(mActivity)
                            .addToRequestQue(new AddCustomerPopup(vendorID, firstname.getText().toString(),
                                    lastName.getText().toString(), email.getText().toString(),
                                    mobile.getText().toString(), editHomePhone.getText().toString(),
                                    editWorkPhone.getText().toString(), editAddress.getText().toString(),
                                    editCity.getText().toString(),
                                    stateList.get(spinState.getSelectedItemPosition()).getStateId(),
                                    countryDataList.get(spinCountry.getSelectedItemPosition()).getCountryD(),
                                    genderArr[spinGender.getSelectedItemPosition()],
                                    String.valueOf(spinPreferredContact.getSelectedItemPosition() + 1),
                                    editCCAppointmentEmail.getText().toString(),
                                    editReferredBy.getText().toString(),
                                    checkSMS.isChecked() ? "1" : "0",
                                    checkSMS.isChecked() ? "1" : "0",
                                    apiCommunicator).getStringRequest());

                }
            }

            private void sendError(CheckBox checkBox) {
                checkBox.setError("Field Cant be Empty");
            }

            private void sendError(EditText mobile) {
                mobile.setError("Field Cant be Empty");
            }

            private void sendValidError(EditText mobile) {
                mobile.setError("Enter valid data.");
            }

        });

        StringRequest countryRequest = new StringRequest(Request.Method.POST,
                Constants.getCountry, response -> {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equalsIgnoreCase("1")) {
                    countryDataList = new GsonBuilder().create().fromJson(response, CountryList.class).getResult();
                    final ArrayList<String> countryList = new ArrayList<>();
                    if (countryDataList != null) {
                        for (CountryData data : countryDataList) {
                            countryList.add(data.getCountryName());
                        }
                    }
                    if (countryList.size() > 0) {
                        spinCountry.setAdapter(new ArrayAdapter<>(mActivity, R.layout.li_spin_search, countryList));

                        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                StringRequest stateRequest = new StringRequest(Request.Method.POST, Constants.getState, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);
                                            String status = jsonObject.getString("status");
                                            if (status.equalsIgnoreCase("1")) {
                                                stateList = new GsonBuilder().create().fromJson(response, GetStateList.class).getResult();
                                                ArrayList<String> list = new ArrayList<>();
                                                for (GetStateData d : stateList) {
                                                    list.add(d.getStateName());
                                                }
                                                if (list.size() > 0) {
                                                    spinState.setAdapter(new ArrayAdapter<>(mActivity, R.layout.li_spin_search, list));
                                                }
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, error -> {
                                    LogUtils.printLog("STate Error", "::  " + error.getMessage());
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        HashMap<String, String> param = new HashMap<>();
                                        param.put("country_id", countryDataList.get(position).getCountryD());
                                        return param;

                                    }
                                };
                                MySingleton.getInstance(mActivity).addToRequestQue(stateRequest);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            LogUtils.printLog("Response", error.getMessage());
        });

        MySingleton.getInstance(mActivity).addToRequestQue(countryRequest);


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window windowAlDl = createCustomerDialog.getWindow();

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        assert windowAlDl != null;
        windowAlDl.setAttributes(layoutParams);

        createCustomerDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MySingleton.getInstance(mActivity)
                .addToRequestQue(new GetCustomers(this, vendorID)
                        .getStringRequest());
        MySingleton.getInstance(mActivity)
                .addToRequestQue(new GetStylists(this, vendorID)
                        .getStringRequest());
        MySingleton.getInstance(mActivity)
                .addToRequestQue(new GetServices(this, vendorID)
                        .getStringRequest());
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("group_appointment")) {
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show();
            finish();
        }
        if (status.equalsIgnoreCase("0")) {
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show();
        }
        if (status.equalsIgnoreCase("get_customers")) {
            customerList = new GsonBuilder().create().fromJson(response, GetCustomer.class).getResult();
            ArrayList<String> customerNames = new ArrayList<>();
            for (GetCustomerData data : customerList) {
                customerNames.add(data.getCustomerName());
            }
//            customerAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_dropdown_item_1line, customerNames);
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(mActivity, R.layout.li_spin_search, customerNames);
            autoCompleteTextView.setAdapter(customerAdapter);
            if (servicList != null && stylistList != null) {
                CallDetail();
            }
        }
        if (status.equalsIgnoreCase("customer_added")) {
            if (!response.equals("error")) {
                customProgress.dismiss();
                createCustomerDialog.dismiss();
                Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show();
                MySingleton.getInstance(mActivity)
                        .addToRequestQue(new GetCustomers(apiCommunicator, vendorID)
                                .getStringRequest());
            }
        }
        if (status.equalsIgnoreCase("get_stylists")) {
            stylistList = new GsonBuilder().create().fromJson(response, GetStylistList.class).getResult();
            if (customerList != null && servicList != null) {
                CallDetail();
            }
        }
        if (status.equalsIgnoreCase("services_list")) {
            servicList = new GsonBuilder().create().fromJson(response, GetServicesList.class).getResult();
            if (customerList != null && stylistList != null) {
                CallDetail();
            }
        }
        if (status.equalsIgnoreCase("group_detail")) {
            List<GroupAppointmentData> list = new GsonBuilder().create().fromJson(response, GroupAppointmentList.class).getGroupAppointmentData();
            List<GetCustomerData> customerDataList = new ArrayList<>();
            List<String> customerIdList = new ArrayList<>();
            List<List<GroupAppointmentModel>> groupAppointmentLlists = new ArrayList<>();
            List<GroupAppointmentModel> groupAppointmentLlistsone = new ArrayList<>();
            for (GroupAppointmentData data : list) {
                customerIdList.add(data.getCustomerId());

            }
            Set<String> ids = new HashSet<>(customerIdList);
            for (String id : ids) {
                for (GetCustomerData data : customerList) {
                    if (id.equalsIgnoreCase(data.getCustomerId())) {
                        customerDataList.add(data);//customer list
                    }
                }
            }
            appointmentAdapter.setList(customerDataList);
            for (GetCustomerData data : customerDataList) {
                List<GroupAppointmentModel> modelLis = new ArrayList<>();
                for (GroupAppointmentData x : list) {
                    if (x.getCustomerId().equalsIgnoreCase(data.getCustomerId())) {
                        SimpleDateFormat format24 = new SimpleDateFormat("HH:mm", Locale.US);
                        String time = null;
                        try {
                            Date date2 = format24.parse(x.getDate().split(" ")[1]);
                            SimpleDateFormat timeformat12 = new SimpleDateFormat("hh:mm aa", Locale.US);
                            time = timeformat12.format(date2);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        GroupAppointmentModel model = new GroupAppointmentModel(x.getDate().split(" ")[0], time, x.getServiceId(), x.getStylistId(), "", x.getDuration());
                        model.setAppointmentId(x.getId());
                        modelLis.add(model);
                    }
                }
                groupAppointmentLlists.add(modelLis);
                appointmentAdapter.updateList(data);
                groupServiceAdapter = new GroupServiceAdapter(modelLis,stylistList,servicList);
                serviceRecyclerView.setAdapter(groupServiceAdapter);
            }
            appointmentList = null;
            appointmentList = groupAppointmentLlists;

            serviceRecyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            setProgress(false);
        }
    }

    private void CallDetail() {
        // Log.e( "CallDetail: ",tokenNumber +"n" );
        if (tokenNumber != null && !tokenNumber.equalsIgnoreCase("0")) {
            setProgress(true);
            //  Log.e( "CallDetail: ","call" );
            MySingleton.getInstance(mActivity)
                    .addToRequestQue(new GroupAppointmentDetailApi(tokenNumber, vendorID, this)
                            .getStringRequest());
        }
    }

    public void setProgress(boolean v) {
        if (v) {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void groupAppointmentCustomerData(GetCustomerData data, String message) {

        if (message.equalsIgnoreCase("added")) {
            ArrayList<GroupAppointmentModel> list = new ArrayList<>();
            GroupAppointmentModel model = new GroupAppointmentModel(date, time, "", "", "0", "0");
            list.add(model);
            appointmentList.add(list);
            if (appointmentList != null && appointmentList.size() == 1) {
                serviceRecyclerView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                groupServiceAdapter = new GroupServiceAdapter(0, appointmentList, stylistList, servicList);
                serviceRecyclerView.setAdapter(groupServiceAdapter);
                selectedCustomer.setText(Html.fromHtml("Add Service For <b>" + data.getCustomerName().split("\n")[0] + "</b>"));
            }
        }

    }

    public String replaceBr(String s) {
        s = s.replace("[", "");
        s = s.replace("]", "");
        s = s.replace(" ", "");
        return s;
    }

    @Override
    public void groupListPos(int pos, String message) {
        if (message.equalsIgnoreCase("deleted")) {
            appointmentList.remove(pos);
        }
        if (message.equalsIgnoreCase("selected")) {
            groupServiceAdapter = null;
            String b = "Add Service For " + appointmentAdapter.getCustomer(pos).getCustomerName().split("\n")[0];
            selectedCustomer.setText(b);
            groupServiceAdapter = new GroupServiceAdapter(pos, appointmentList, stylistList, servicList);
            serviceRecyclerView.setAdapter(groupServiceAdapter);

        }
    }

    @Override
    public void emptyCustomerListNotify() {
        serviceRecyclerView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
    }
}