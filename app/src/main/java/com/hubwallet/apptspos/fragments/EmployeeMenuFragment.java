package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hubwallet.apptspos.R;

import java.util.Objects;


public class EmployeeMenuFragment extends Fragment implements View.OnClickListener {
    LinearLayout tabEmployee,tabAttendance,tabSchedule;
    TextView tvEmployee,tvAttendance,tvSchedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_menu, container, false);
        tabEmployee = view.findViewById(R.id.tabEmployee);
        tabAttendance = view.findViewById(R.id.tabAttendance);
        tabSchedule = view.findViewById(R.id.tabSchedule);
        tvEmployee = view.findViewById(R.id.employee);
        tvAttendance = view.findViewById(R.id.attendance);
        tvSchedule = view.findViewById(R.id.schedule);
        tabEmployee.setOnClickListener(this);
        tabAttendance.setOnClickListener(this);
        tvEmployee.setTextColor(getResources().getColor(R.color.colorPrimary));
        StylishFragment stylishFragment = new StylishFragment();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerEmpMenu, stylishFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tabEmployee:
                tvEmployee.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvAttendance.setTextColor(getResources().getColor(R.color.setting_colour));
                tvSchedule.setTextColor(getResources().getColor(R.color.setting_colour));
                StylishFragment stylishFragment = new StylishFragment();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerEmpMenu, stylishFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabAttendance:
                tvEmployee.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAttendance.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvSchedule.setTextColor(getResources().getColor(R.color.setting_colour));
                AttendanceListFragment attendanceListFragment = new AttendanceListFragment();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerEmpMenu, attendanceListFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabSchedule:
                tvEmployee.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAttendance.setTextColor(getResources().getColor(R.color.setting_colour));
                tvSchedule.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }
}
