package com.hubwallet.apptspos.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.request.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.hubwallet.apptspos.APis.AddBrand;
import com.hubwallet.apptspos.APis.GetBusinessHour;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.UpdateCalendar;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.CalenderAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.CalendarJson;
import com.hubwallet.apptspos.Utils.CommanInterface;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalenderResponce;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CalanderConfigFragment extends Fragment implements ApiCommunicator, CommanInterface {

    private static final String TAG = CalanderConfigFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private Button updateCalendar;
    private CalenderAdapter calenderAdapter;
    String vendorId = String.valueOf(1);
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    List<CalendarJson> list = new ArrayList<>();
    private int mYear, mMonth, mDay, mHour, mMinute,mAm;
    private List<CalandarData> calanderDatalist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calanser_config, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        updateCalendar = view.findViewById(R.id.btnUpdateCalendar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //   calenderAdapter = new CalenderAdapter(getActivity(), arrayListCallBack);
        // recyclerCalendar.setAdapter(calenderAdapter);
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetBusinessHour(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);

        updateCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).onProgress();
                ArrayList<HashMap<String,String>> daysData = new ArrayList<>();
                for (CalandarData calandarData:calanderDatalist){
                     HashMap<String,String> dayDataMap = new HashMap<>();
                     dayDataMap.put("switch",calandarData.getmSwitch());
                     dayDataMap.put("day",calandarData.getmDays());
                     dayDataMap.put("from",calandarData.getmStartTime());
                     dayDataMap.put("to",calandarData.getmEndTime());
                     daysData.add(dayDataMap);
                }
                Gson json = new Gson();
                String days= json.toJson(daysData);
                Log.d(TAG, "onClick: data of day : "+days);
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new UpdateCalendar(new PrefManager(getContext())
                                .getVendorId(),days, apiCommunicator).getStringRequest());
            }
        });

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("update_calendar")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            StringRequest stringRequest = new GetBusinessHour(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
            stringRequest.setShouldCache(false);
            MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        }
        if (status.equalsIgnoreCase("get_business_hour")) {
            calanderDatalist = new GsonBuilder().create().fromJson(response, CalenderResponce.class).getResult();
            setAdapter(calanderDatalist);
        }

    }



    private void setAdapter(List<CalandarData> list) {
        calenderAdapter= new CalenderAdapter(getContext(), list, apiCommunicator,this);
        recyclerView.setAdapter(calenderAdapter);
        // recyclerView.setAdapter(new CategoryListAdapter(getContext(), list, apiCommunicator));
    }


    private void getTimeCurrent(View v,int position) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       // btnUpdateCalender.setText(hourOfDay + ":" + minute);
                        String am_pm ="AM";
                        if(hourOfDay > 11) {
                            am_pm = "PM";
                        }
                        int hour_of_12_hour_format;
                        if (hourOfDay > 11){
                            hour_of_12_hour_format = hourOfDay-12;
                        }else {
                            hour_of_12_hour_format = hourOfDay;
                        }
                        String time = hour_of_12_hour_format + ":" + minute + " " + am_pm ;

                        if(v.getId() == R.id.fromTV){
                            calenderAdapter.setTimeOnView(time,position,0);
                        }else if (v.getId() == R.id.toTV){
                            calenderAdapter.setTimeOnView(time,position,1);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void selectTime(View view, int position,List<CalandarData> calandarData) {
        getTimeCurrent(view,position);
        this.calanderDatalist = calandarData;
    }

}

