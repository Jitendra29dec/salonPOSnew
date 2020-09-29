package com.hubwallet.apptspos.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.hubwallet.apptspos.APis.GetBusinessHour;
import com.hubwallet.apptspos.APis.GetCalendarColor;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateColor;
import com.hubwallet.apptspos.APis.UpdatePermission;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.CalendarColorAdapter;
import com.hubwallet.apptspos.Adapters.CalenderAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.ColorPikInterface;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.LongTypeAdapter;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalenderResponce;
import com.hubwallet.apptspos.Utils.Models.CalendarColorData;
import com.hubwallet.apptspos.Utils.Models.CalendarColorResponse;
import com.hubwallet.apptspos.Utils.PrefManager;


import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;


public class CalendarColourFragment extends Fragment implements ApiCommunicator, ColorPikInterface {
    private int DefaultColour;
    private RecyclerView recyclerView;
    FrameLayout layout;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    CalendarColorAdapter adapter;
    private List<CalendarColorData> colorList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_colour, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        layout = view.findViewById(R.id.mainLayout);
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetCalendarColor(new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
      //  openColourPicker();
        return view;
    }

    private void openColourPicker(View view,int position) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getContext(), DefaultColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                DefaultColour = color;
                String hexColor = String.format("#%06X", (0xFFFFFF & color));
                if(view.getId() == R.id.color){
                    adapter.setColorOnView(hexColor,position,0);
                }else if (view.getId() == R.id.textColor){
                    adapter.setColorOnView(hexColor,position,1);
                }
            }
        });
        ambilWarnaDialog.show();
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("update_color_code")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            StringRequest stringRequest = new GetCalendarColor(new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest();
            stringRequest.setShouldCache(false);
            MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        }

        if (status.equalsIgnoreCase("get_color_list")) {
            colorList = new GsonBuilder().create().fromJson(response, CalendarColorResponse.class).getResult();
            setAdapter(colorList);
        }
    }

    private void setAdapter(List<CalendarColorData> list) {
        adapter= new CalendarColorAdapter(getContext(), list, apiCommunicator,this);
        recyclerView.setAdapter(adapter);
        // recyclerView.setAdapter(new CategoryListAdapter(getContext(), list, apiCommunicator));
    }

    @Override
    public void onClick(View view, int position) {
        openColourPicker(view,position);
    }

    @Override
    public void updateColor(View view, int position,String colorId) {
        ((NavigationActivity) getActivity()).onProgress();
        String colorCode = colorList.get(position).getColorCode();
        MySingleton.getInstance(getContext())
                .addToRequestQue(new UpdateColor(new PrefManager(getContext())
                        .getVendorId(),colorId,colorCode, apiCommunicator).getStringRequest());
    }
}
