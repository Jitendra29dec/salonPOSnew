package com.hubwallet.apptspos.Adapters;

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
import com.hubwallet.apptspos.Utils.Models.AccessLevelModuleData;
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceData;
import com.hubwallet.apptspos.Utils.Models.GetPermissionData;
import com.hubwallet.apptspos.Utils.Models.SettingEmpData;

import java.util.List;

public class AcessLevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SettingEmpData> list;
    List<AccessLevelModuleData> moduleData;
    private ApiCommunicator apiCommunicator;
    private int HEADER = 1111;
    private int CONTENT = 1231;

    public AcessLevelAdapter(Context context, List<SettingEmpData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.apiCommunicator = apiCommunicator;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0) {
            type = HEADER;
        } else {
            type = CONTENT;
        }
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        if (i == HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_access_level_heading, null, false);
            v.setLayoutParams(lp);
            viewHolder = new AcessLevelListHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_access_level_list, null, false);
            v.setLayoutParams(lp);
            viewHolder = new AcessLevelListHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == CONTENT) {
            final SettingEmpData data = list.get(i);
            AcessLevelListHolder holder = (AcessLevelListHolder) viewHolder;
            if (i % 2 == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
            }
            holder.employee.setText(data.getStylistName());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class AcessLevelListHolder extends RecyclerView.ViewHolder {
        TextView employee, clockIn, breakStart, breakEnd, clockOut, emergClockOut, scheduleForTime, scheduleToTime, workTime;

        public AcessLevelListHolder(@NonNull View itemView) {
            super(itemView);
            employee = itemView.findViewById(R.id.customer);
        }
    }


}




