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
import com.hubwallet.apptspos.APis.GetIouAmount;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateIou;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.IouAmountData;
import com.hubwallet.apptspos.Utils.Models.LockTimeData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;


public class IouFragment extends Fragment implements ApiCommunicator {
    private EditText iouMaxAmount;
    private AppCompatTextView submitButton;
    ApiCommunicator apiCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_iou, container, false);
        apiCommunicator = this;
        iouMaxAmount = view.findViewById(R.id.iouMAxAmount);
        submitButton = view.findViewById(R.id.submitButton);
        ((NavigationActivity) getActivity()).onProgress();
        MySingleton.getInstance(getContext()).addToRequestQue(new GetIouAmount(new PrefManager(getContext()).getVendorId(),apiCommunicator).getStringRequest());
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).onProgress();
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new UpdateIou(new PrefManager(getContext())
                                .getVendorId(),iouMaxAmount.getText().toString(), apiCommunicator).getStringRequest());
            }
        });
        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("update_iou")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            MySingleton.getInstance(getContext()).addToRequestQue(new GetIouAmount(new PrefManager(getContext()).getVendorId(),apiCommunicator).getStringRequest());
          /*  try {
                JSONObject jsonObject = new JSONObject(response);
                IouAmountData iouAmountData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), IouAmountData.class);
                getDataFromApi(iouAmountData);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
        if (status.equalsIgnoreCase("get_IouAmount")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                IouAmountData iouAmountData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), IouAmountData.class);
                getDataFromApi(iouAmountData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void getDataFromApi(IouAmountData data){
        iouMaxAmount.setText(data.getAmount());
    }
}
