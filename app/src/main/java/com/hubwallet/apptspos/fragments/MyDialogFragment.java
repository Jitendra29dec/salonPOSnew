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

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetAttendance;
import com.hubwallet.apptspos.APis.GetBreakLeave;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Adapters.AttendanceAdapter;
import com.hubwallet.apptspos.Adapters.DialogBreakAdapter;
import com.hubwallet.apptspos.Adapters.DialogClockAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.BreakOnClick;
import com.hubwallet.apptspos.Utils.EmployeeOnClick;
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceData;
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceList;
import com.hubwallet.apptspos.Utils.Models.BreakLeave;
import com.hubwallet.apptspos.Utils.Models.BreakLeaveResponse;
import com.hubwallet.apptspos.Utils.Models.ClockIn;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDialogFragment extends DialogFragment implements ApiCommunicator, BreakOnClick {
  //  private ApiInterface apiService;
    private RecyclerView recyclerBreakTime, recycleClock;
    //  private CommonProgressDialog commonProgressDialog;
    private DialogClockAdapter dialogClockAdapter;
    private DialogBreakAdapter dialogBreakAdapter;
    ArrayList<ClockIn> arrayList;
    ArrayList<BreakLeave> breakLeaveArrayList;
    DatePickerDialog picker;
    TextView editDateFrom, editDateTo;

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
        return inflater.inflate(R.layout.fragment_my_dialog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  apiService = ApiClient.getClient().create(ApiInterface.class);
        // commonProgressDialog = new CommonProgressDialog(getActivity());
        arrayList = new ArrayList<>();
        breakLeaveArrayList = new ArrayList<>();

        editDateFrom = view.findViewById(R.id.editDateFrom);
        editDateTo = view.findViewById(R.id.editDateTo);
        recyclerBreakTime = view.findViewById(R.id.recyclerBreakTime);
        recycleClock = view.findViewById(R.id.recycleClock);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        recyclerBreakTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
       // dialogClockAdapter = new DialogClockAdapter(getActivity(), arrayList);
       // recyclerBreakTime.setAdapter(dialogClockAdapter);

        recycleClock.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
      //  dialogBreakAdapter = new DialogBreakAdapter(getActivity(), breakLeaveArrayList);
      //  recycleClock.setAdapter(dialogBreakAdapter);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetBreakLeave("69","","", this).getStringRequest());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Submit", Toast.LENGTH_SHORT).show();
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
        if (status.equalsIgnoreCase("break_leave_list")) {
            List<BreakLeave> list = new GsonBuilder().create().fromJson(response, BreakLeaveResponse.class).getData().getBreakLeave();
            setAdapter(list);
            List<ClockIn> clockList = new GsonBuilder().create().fromJson(response, BreakLeaveResponse.class).getData().getClockin();
            setAdapter1(clockList);
        }
    }

    private void setAdapter(List<BreakLeave> list) {
        recyclerBreakTime.setAdapter(new DialogBreakAdapter(getContext(), list, this,this));

    }

    private void setAdapter1(List<ClockIn> list) {
        recycleClock.setAdapter(new DialogClockAdapter(getContext(), list, this));

    }

    @Override
    public void breakClick(View view, int position) {
        BreakTimeFragment dialogFragment = new BreakTimeFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerEmpMenu, dialogFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }

}
