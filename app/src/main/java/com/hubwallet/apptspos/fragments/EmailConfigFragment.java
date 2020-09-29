package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetNotification;
import com.hubwallet.apptspos.APis.GetTaxes;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateIou;
import com.hubwallet.apptspos.APis.UpdateNotification;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.IouAmountData;
import com.hubwallet.apptspos.Utils.Models.NotificationData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;


public class EmailConfigFragment extends Fragment implements ApiCommunicator {
    private SwitchCompat appointSwitch, requestSwitch, reminderSwitch, notificationSwitch, stylistSwitch;
    private EditText requestTime, reminderTime;
    ApiCommunicator apiCommunicator;
    AppCompatTextView submitButton;
    private String appointSwitchIsActive, requestSwitchIsActive, reminderSwitchIsActive, notificationSwitchIsActive, stylistSwitchIsActive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email_config, container, false);
        apiCommunicator = this;
        appointSwitch = view.findViewById(R.id.appointSwitch);
        requestSwitch = view.findViewById(R.id.requestSwitch);
        reminderSwitch = view.findViewById(R.id.reminderSwitch);
        notificationSwitch = view.findViewById(R.id.notificationSwitch);
        stylistSwitch = view.findViewById(R.id.stylistSwitch);
        requestTime = view.findViewById(R.id.requestTime);
        reminderTime = view.findViewById(R.id.reminderTime);
        submitButton = view.findViewById(R.id.submitButton);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetNotification(new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());
        updateSwitch();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new UpdateNotification(new PrefManager(getContext())
                                .getVendorId(), appointSwitchIsActive, requestSwitchIsActive, requestTime.getText().toString(), reminderSwitchIsActive,
                                reminderTime.getText().toString(), notificationSwitchIsActive, stylistSwitchIsActive, apiCommunicator).getStringRequest());
            }
        });

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("update_notification")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject(response);
                NotificationData notificationData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), NotificationData.class);
                getNotificationDataFromApi(notificationData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (status.equalsIgnoreCase("get_notification")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                NotificationData notificationData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), NotificationData.class);
                getNotificationDataFromApi(notificationData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getNotificationDataFromApi(NotificationData notificationData) {
        if (notificationData.getAppointmentDetail().equalsIgnoreCase("1")) {
            appointSwitch.setChecked(true);
            appointSwitchIsActive = "1";
        } else {
            appointSwitch.setChecked(false);
            appointSwitchIsActive = "0";
        }
        if (notificationData.getConfirmation().equalsIgnoreCase("1")) {
            requestSwitch.setChecked(true);
            requestSwitchIsActive = "1";
        } else {
            requestSwitch.setChecked(false);
            requestSwitchIsActive = "0";
        }
        if (notificationData.getAppointmentReminder().equalsIgnoreCase("1")) {
            reminderSwitch.setChecked(true);
            reminderSwitchIsActive = "1";
        } else {
            reminderSwitch.setChecked(false);
            reminderSwitchIsActive = "0";
        }
        if (notificationData.getBusinessNotification().equalsIgnoreCase("1")) {
            notificationSwitch.setChecked(true);
            notificationSwitchIsActive = "1";
        } else {
            notificationSwitch.setChecked(false);
            notificationSwitchIsActive = "0";
        }
        if (notificationData.getStylistNotification().equalsIgnoreCase("1")) {
            stylistSwitch.setChecked(true);
            stylistSwitchIsActive = "1";
        } else {
            stylistSwitch.setChecked(false);
            stylistSwitchIsActive = "0";
        }
        requestTime.setText(notificationData.getConfirmationTime());
        reminderTime.setText(notificationData.getAppointmentReminderTime());

    }

    private void updateSwitch() {
        appointSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    appointSwitchIsActive = "1";
                } else {
                    appointSwitchIsActive = "0";

                }
            }
        });
        requestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    requestSwitchIsActive = "1";
                } else {
                    requestSwitchIsActive = "0";

                }
            }
        });

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    reminderSwitchIsActive = "1";
                } else {
                    reminderSwitchIsActive = "0";

                }
            }
        });

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    notificationSwitchIsActive = "1";
                } else {
                    notificationSwitchIsActive = "0";

                }
            }
        });

        stylistSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    stylistSwitchIsActive = "1";
                } else {
                    stylistSwitchIsActive = "0";

                }
            }
        });
    }
}
