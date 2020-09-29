package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddBrand;
import com.hubwallet.apptspos.APis.GetCategoryById;
import com.hubwallet.apptspos.APis.GetLockTime;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateLockTime;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetCategoryByData;
import com.hubwallet.apptspos.Utils.Models.LockTimeData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;


public class ScreenLockTimeFragment extends Fragment implements ApiCommunicator {
    private EditText lockTime, warningTime;
    private RadioGroup rgLockScreen;
    private RadioButton rboN, rbOFF;
    ApiCommunicator apiCommunicator;
    private Communicator communicator;
    private AppCompatTextView submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_screen_lock_time, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        lockTime = view.findViewById(R.id.lockTime);
        warningTime = view.findViewById(R.id.waringTime);
        rgLockScreen = view.findViewById(R.id.rgLockScreen);
        rbOFF = view.findViewById(R.id.rbOFF);
        rboN = view.findViewById(R.id.rbON);
        submitButton = view.findViewById(R.id.submitButton);
        ((NavigationActivity) getActivity()).onProgress();
        MySingleton.getInstance(getContext()).addToRequestQue(new GetLockTime(new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());

        submitButton.setOnClickListener(view1 -> {
            ((NavigationActivity) getActivity()).onProgress();
            MySingleton.getInstance(getContext())
                    .addToRequestQue(new UpdateLockTime(new PrefManager(getContext())
                            .getVendorId(), lockTime.getText().toString(), apiCommunicator).getStringRequest());
        });

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("update_lock_time")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            MySingleton.getInstance(getContext()).addToRequestQue(new GetLockTime(new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());
        }
        if (status.equalsIgnoreCase("get_lockTime")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                LockTimeData lockTimeData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), LockTimeData.class);
                getDataFromApi(lockTimeData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDataFromApi(LockTimeData data) {
        lockTime.setText(data.getLockTime());
    }

}
