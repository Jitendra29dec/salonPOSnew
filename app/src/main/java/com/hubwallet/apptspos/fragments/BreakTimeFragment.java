package com.hubwallet.apptspos.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetBreakLeave;
import com.hubwallet.apptspos.APis.GetBreakList;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Adapters.BreakAdapter;
import com.hubwallet.apptspos.Adapters.DialogBreakAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.BreakData;
import com.hubwallet.apptspos.Utils.Models.BreakLeave;
import com.hubwallet.apptspos.Utils.Models.BreakLeaveResponse;
import com.hubwallet.apptspos.Utils.Models.BreakResponse;
import com.hubwallet.apptspos.Utils.Models.ClockIn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreakTimeFragment extends DialogFragment implements ApiCommunicator {
    // private ApiInterface apiService;
    // private CommonProgressDialog commonProgressDialog;
    private BreakAdapter breakAdapter;
    private RecyclerView recyclerBreakTime;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        if (getArguments() != null) {
            if (getArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert Dialog");
        builder.setMessage("Alert Dialog inside DialogFragment");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_break_time, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // commonProgressDialog = new CommonProgressDialog(getActivity());
        recyclerBreakTime = view.findViewById(R.id.recyclerBreakTime);
        recyclerBreakTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        MySingleton.getInstance(getContext()).addToRequestQue(new GetBreakList("69", "2020-07-24",this).getStringRequest());
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("break_Date_wise")) {
            List<BreakData> list = new GsonBuilder().create().fromJson(response, BreakResponse.class).getData();
            setAdapter(list);
        }
    }

    private void setAdapter(List<BreakData> list) {
        recyclerBreakTime.setAdapter(new BreakAdapter(getContext(), list, this));

    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }

}
