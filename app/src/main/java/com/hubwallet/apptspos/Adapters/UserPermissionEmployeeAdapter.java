package com.hubwallet.apptspos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.EmployeeOnClick;
import com.hubwallet.apptspos.Utils.Models.EmployeePermissionData;
import com.hubwallet.apptspos.Utils.Models.GetPermissionData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPermissionEmployeeAdapter extends RecyclerView.Adapter<UserPermissionEmployeeAdapter.MyViewHolder> {
    private Context context;
    List<EmployeePermissionData> arrayListCallBack;
    private EmployeeOnClick employeeOnClick;
    ApiCommunicator apiCommunicator;
    List<GetPermissionData> permissionData;

    public UserPermissionEmployeeAdapter(Context context, List<EmployeePermissionData> resultEmployeePermissionArrayList, ApiCommunicator communicator,
                                         EmployeeOnClick employeeOnClick) {
        this.context = context;
        this.arrayListCallBack = resultEmployeePermissionArrayList;
        this.apiCommunicator = communicator;
        this.employeeOnClick = employeeOnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employeepermission_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.checkTextPermission.setOnCheckedChangeListener(null);

        holder.checkTextPermission.setChecked(arrayListCallBack.get(position).isSelect());
        holder.checkTextPermission.setText(arrayListCallBack.get(position).getPermission());

        holder.checkTextPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                arrayListCallBack.get(position).setSelect(b);
            }
        });
        holder.checkTextPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeOnClick.EmployeePermissionMulti(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListCallBack.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkTextPermission)
        CheckBox checkTextPermission;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setCallInterface(EmployeeOnClick employeeOnClick) {
        this.employeeOnClick = employeeOnClick;
    }

    public void setCheckedList(List<GetPermissionData> data) {
        this.permissionData = data;
        for (int k=0;k<arrayListCallBack.size();k++){
            arrayListCallBack.get(k).setSelect(false);
            //notifyItemChanged(k);
        }
        notifyDataSetChanged();
        for (int i=0; i<data.size();i++){
            GetPermissionData permissionData = this.permissionData.get(i);
            for (int j=0; j<arrayListCallBack.size();j++){
                if (arrayListCallBack.get(j).getPermissionId().equals(permissionData.getPermissionId())){
                    arrayListCallBack.get(j).setSelect(true);
                    notifyItemChanged(j);
                }
            }
        }
    }
}
