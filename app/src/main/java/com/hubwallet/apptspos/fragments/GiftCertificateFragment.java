package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetGiftCertificate;
import com.hubwallet.apptspos.APis.GetIouAmount;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateGiftCertificate;
import com.hubwallet.apptspos.APis.UpdateLockTime;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GiftAmountData;
import com.hubwallet.apptspos.Utils.Models.LockTimeData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;


public class GiftCertificateFragment extends Fragment implements ApiCommunicator {
    private EditText giftMaxAmount;
    private AppCompatTextView submitButton;
    ApiCommunicator apiCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gift_certificate, container, false);
        apiCommunicator = this;
        giftMaxAmount = view.findViewById(R.id.giftMaxAmount);
        submitButton = view.findViewById(R.id.submitButton);
        ((NavigationActivity) getActivity()).onProgress();
        MySingleton.getInstance(getContext()).addToRequestQue(new GetGiftCertificate(new PrefManager(getContext()).getVendorId(),apiCommunicator).getStringRequest());
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).onProgress();
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new UpdateGiftCertificate(new PrefManager(getContext())
                                .getVendorId(),giftMaxAmount.getText().toString(), apiCommunicator).getStringRequest());
            }
        });
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("update_certificate")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            MySingleton.getInstance(getContext()).addToRequestQue(new GetGiftCertificate(new PrefManager(getContext()).getVendorId(),apiCommunicator).getStringRequest());
          /*  try {
                JSONObject jsonObject = new JSONObject(response);
                GiftAmountData giftAmountData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GiftAmountData.class);
                getDataFromApi(giftAmountData);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

        }
        if (status.equalsIgnoreCase("get_certificate")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GiftAmountData giftAmountData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GiftAmountData.class);
                getDataFromApi(giftAmountData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDataFromApi(GiftAmountData data){
        giftMaxAmount.setText(data.getAmount());
    }
}
