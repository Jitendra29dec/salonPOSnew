package com.hubwallet.apptspos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.EmployeeOnClick;
import com.hubwallet.apptspos.Utils.Models.GetPermissionData;
import com.hubwallet.apptspos.Utils.Models.SettingEmpData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPermissionAdapter extends RecyclerView.Adapter<UserPermissionAdapter.MyViewHolder> {
    private Context context;
    List<SettingEmpData> arrayListCallBack;
    ApiCommunicator apiCommunicator;
    private EmployeeOnClick employeeOnClick;
    private int row_index;

    public UserPermissionAdapter(Context activity, List<SettingEmpData> arrayListCallBack,ApiCommunicator communicator,
                                 EmployeeOnClick employeeOnClick) {
        this.context = activity;
        this.arrayListCallBack = arrayListCallBack;
        apiCommunicator = communicator;
        this.employeeOnClick = employeeOnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textEmployeeName.setText(arrayListCallBack.get(position).getStylistName());
        holder.textEmployeeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                notifyDataSetChanged();
                employeeOnClick.EmployeeOne(v, position,arrayListCallBack.get(position).getStylistId());
            }
        });
        if (row_index==position){
            holder.textEmployeeName.setBackgroundColor(Color.LTGRAY);
        }else {
            holder.textEmployeeName.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayListCallBack.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textEmployeeName)
        TextView textEmployeeName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

   /* public void setCallInterface(EmployeeOnClick commanInterface1) {
        this.commanInterface = commanInterface1;
    }*/
}
