package com.hubwallet.apptspos.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetAttendance;
import com.hubwallet.apptspos.APis.GetBreakLeave;
import com.hubwallet.apptspos.APis.GetBreakList;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.AttendanceAdapter;
import com.hubwallet.apptspos.Adapters.BreakAdapter;
import com.hubwallet.apptspos.Adapters.DialogBreakAdapter;
import com.hubwallet.apptspos.Adapters.DialogClockAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.BreakOnClick;
import com.hubwallet.apptspos.Utils.CommanInterface;
import com.hubwallet.apptspos.Utils.EmployeeOnClick;
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceData;
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceList;
import com.hubwallet.apptspos.Utils.Models.BreakData;
import com.hubwallet.apptspos.Utils.Models.BreakLeave;
import com.hubwallet.apptspos.Utils.Models.BreakLeaveResponse;
import com.hubwallet.apptspos.Utils.Models.BreakResponse;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;
import com.hubwallet.apptspos.Utils.Models.ClockIn;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AttendanceListFragment extends Fragment implements ApiCommunicator, EmployeeOnClick, BreakOnClick {
    private RecyclerView recyclerView;
    private TextView datFrom, dateTo;
    private String date;
    private Button btnSearch;
    AttendanceAdapter adapter;
    private String stylistId = "";
    DatePickerDialog picker;
    TextView editDateFrom, editDateTo;
    RecyclerView recyclerBreakTime, recycleClock;
    private RecyclerView recyclerTotalBreakTime;
    AlertDialog dialog, totalbrekDialog;
    private Spinner filterSpinner, emergencyClock;
    ApiCommunicator apiCommunicator;
    ArrayList<String> filterList = new ArrayList<>();
    ArrayList<String> emergencyClockList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stylistId = getArguments().getString("stylist_id", "");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance_list, null, false);
        apiCommunicator = this;
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        datFrom = view.findViewById(R.id.dateFrom);
        dateTo = view.findViewById(R.id.dateTo);
        filterSpinner = view.findViewById(R.id.filterSpinner);
        emergencyClock = view.findViewById(R.id.spnEmergencyClock);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MySingleton.getInstance(getContext()).addToRequestQue(new GetAttendance(new PrefManager(getContext()).getVendorId(), "","","","",this).getStringRequest());

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        filterList.add("week");
        filterList.add("month");
        filterList.add("year");
        setSpinner(filterList, filterSpinner);

        emergencyClockList.add("No");
        emergencyClockList.add("Yes");
        setSpinner(emergencyClockList, emergencyClock);


        datFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    datFrom.setText(date);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    dateTo.setText(date);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).onProgress();
                int emergencyClockValue;
                String filterType;
                emergencyClockValue = emergencyClock.getSelectedItemPosition();
                filterType = filterSpinner.getSelectedItem().toString();
                MySingleton.getInstance(getContext()).addToRequestQue(new GetAttendance(new PrefManager(getContext()).getVendorId(),filterType,datFrom.getText().toString(),dateTo.getText().toString(),String.valueOf(emergencyClockValue), AttendanceListFragment.this).getStringRequest());
            }
        });

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("attendance_list")) {
            List<AttendanceData> list = new GsonBuilder().create().fromJson(response, AttendanceList.class).getAttendance();
            setAdapter(list);
        }

        if (status.equalsIgnoreCase("break_leave_list")) {
            List<BreakLeave> list = new GsonBuilder().create().fromJson(response, BreakLeaveResponse.class).getData().getBreakLeave();
            setAdapter2(list);
            List<ClockIn> clockList = new GsonBuilder().create().fromJson(response, BreakLeaveResponse.class).getData().getClockin();
            setAdapter1(clockList);
        }
        if (status.equalsIgnoreCase("break_Date_wise")) {
            List<BreakData> list = new GsonBuilder().create().fromJson(response, BreakResponse.class).getData();
            setAdapter3(list);
        }
       /* if (status.equalsIgnoreCase("get_order_detail")) {
            Communicator communicator = (Communicator) getActivity();
            communicator.sendmessage("order_detail", response);
        }*/
    }

    private void setAdapter(List<AttendanceData> list) {
        recyclerView.setAdapter(new AttendanceAdapter(getContext(), list, this, this));

    }

    @Override
    public void EmployeeOne(View view, int position, String emp_id) {
        String stylistId = emp_id;
        breakTimeSchedule(stylistId);
     /*   MyDialogFragment myDialogFragment = new MyDialogFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerEmpMenu, myDialogFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();*/
    }

    @Override
    public void EmployeePermissionMulti(View view, int position) {

    }


    private void breakTimeSchedule(String stylistId) {
        //  Rect displayRectangle = new Rect();
        //   Window window = getActivity().getWindow();
        //  window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        dialog = new AlertDialog.Builder(getContext(), R.style.customDialog3).create();
        dialog.setCancelable(false);
        // Dialog d = builder.setView(new View(getContext())).create();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_dialog, null, false);
        //  WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        // lp.copyFrom(d.getWindow().getAttributes());
        // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //  lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        // view.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
        //  view.setMinimumHeight((int)(displayRectangle.height() * 0.9f));
        dialog.setView(view);
        ArrayList<ClockIn> arrayList;
        ArrayList<BreakLeave> breakLeaveArrayList;


        editDateFrom = view.findViewById(R.id.editDateFrom);
        editDateTo = view.findViewById(R.id.editDateTo);
        recyclerBreakTime = view.findViewById(R.id.recyclerBreakTime);
        recycleClock = view.findViewById(R.id.recycleClock);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        recyclerBreakTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleClock.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        MySingleton.getInstance(getContext()).addToRequestQue(new GetBreakLeave(stylistId, "", "", this).getStringRequest());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySingleton.getInstance(getContext()).addToRequestQue(new GetBreakLeave("69", editDateFrom.getText().toString(), editDateTo.getText().toString()
                        , AttendanceListFragment.this).getStringRequest());
                Toast.makeText(getActivity(), "Submit", Toast.LENGTH_SHORT).show();
                ((NavigationActivity) getActivity()).onProgress();
            }
        });
        editDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFromMehtod();
            }
        });
        editDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "To Date", Toast.LENGTH_SHORT).show();
                DateToMethod();
            }
        });
        ImageView calcel = view.findViewById(R.id.imageCancel);
        calcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

        //  d.getWindow().setAttributes(lp);
    }

    private void DateToMethod() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editDateTo.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);
        picker.show();
    }

    private void DateFromMehtod() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editDateFrom.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);
        picker.show();
    }

    private void setAdapter2(List<BreakLeave> list) {
        recyclerBreakTime.setAdapter(new DialogBreakAdapter(getContext(), list, this, this));

    }

    private void setAdapter1(List<ClockIn> list) {
        recycleClock.setAdapter(new DialogClockAdapter(getContext(), list, this));

    }

    @Override
    public void breakClick(View view, int position) {
        dialog.dismiss();
        totalBreakTimeSchedule();
    }

    private void totalBreakTimeSchedule() {
        //  Rect displayRectangle = new Rect();
        //   Window window = getActivity().getWindow();
        //  window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        totalbrekDialog = new AlertDialog.Builder(getContext(), R.style.customDialog3).create();
        totalbrekDialog.setCancelable(false);
        // Dialog d = builder.setView(new View(getContext())).create();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_break_time, null, false);
        //  WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //  lp.copyFrom(d.getWindow().getAttributes());
        //  lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //  lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        // view.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
        //  view.setMinimumHeight((int)(displayRectangle.height() * 0.9f));
        totalbrekDialog.setView(view);

        recyclerTotalBreakTime = view.findViewById(R.id.recyclerBreakTime);
        recyclerTotalBreakTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        MySingleton.getInstance(getContext()).addToRequestQue(new GetBreakList("69", "2020-07-24", this).getStringRequest());
        ImageView calcel = view.findViewById(R.id.imageCancel);
        calcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalbrekDialog.dismiss();
            }
        });
        totalbrekDialog.show();
        //  d.getWindow().setAttributes(lp);
    }

    private void setAdapter3(List<BreakData> list) {
        recyclerTotalBreakTime.setAdapter(new BreakAdapter(getContext(), list, this));

    }

    private void setSpinner(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }
}
