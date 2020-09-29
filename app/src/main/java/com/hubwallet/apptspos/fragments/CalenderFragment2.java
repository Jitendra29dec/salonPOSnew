package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Communicator;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class
CalenderFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.calendar_fragment123, null, false);
        final CalendarView calendarView = v.findViewById(R.id.calendarView12);
        final TextView date, timeView;
        // timeView = v.findViewById(R.id.timeView);
        // /   Button chk=v.findViewById(R.id.checkout);
        //    chk.setOnClickListener(new View.OnClickListener() {
        //       @Override
        //         public void onClick(View v) {
        //         Communicator communicator= (Communicator) getContext();
        //          communicator.sendmessage("1");
        //          }
        //       });
        Button create = v.findViewById(R.id.createCustomer);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator communicator = (Communicator) getContext();
                //    communicator.sendmessage("1");
            }
        });
        //timeView.setOnClickListener(new View.OnClickListener() {
        //      @Override
        ///      public void onClick(View v) {
        //           Calendar c = Calendar.getInstance();
        //         // From calander get the year, month, day, hour, minute
        //           final int hour = c.get(Calendar.HOUR_OF_DAY);
        //           int minute = c.get(Calendar.MINUTE);
        //           new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
        //           @Override
        //              public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //               String ti = getTime(hourOfDay, minute);
        //                  timeView.setText(ti);
        //              }
        //           }, hour, minute, true).show();
        //       }
        //      });
        //    date = v.findViewById(R.id.dateView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //          date.setText(year + "/" + month + "/" + dayOfMonth);
            }
        });
        return v;
    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }
}

