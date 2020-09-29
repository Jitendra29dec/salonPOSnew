package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.CommanInterface;
import com.hubwallet.apptspos.Utils.EmployeeOnClick;
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceData;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<AttendanceData> list;
    private ApiCommunicator apiCommunicator;
    private int HEADER = 1111;
    private int CONTENT = 1231;
    EmployeeOnClick employeeOnClick;


    public AttendanceAdapter(Context context, List<AttendanceData> list, ApiCommunicator apiCommunicator,
                             EmployeeOnClick employeeOnClick) {

        this.context = context;
        this.list = list;
        this.list.add(0,null);
        this.apiCommunicator = apiCommunicator;
        this.employeeOnClick = employeeOnClick;
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
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_attendance_list_header, null, false);
            v.setLayoutParams(lp);
            viewHolder = new AttendanceListHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_attendance_list, null, false);
            v.setLayoutParams(lp);
            viewHolder = new AttendanceListHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == CONTENT) {
            final AttendanceData data = list.get(i);
            AttendanceListHolder holder = (AttendanceListHolder) viewHolder;
            if (i %2==1){
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else {
                holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
            }
           /* holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiCommunicator.getApiData("get_order_detail", data.getOrderId());
                }
            });*/
           /* if (data.getAttendanceId() != null) {
                holder.oderNumber.setText(data.getOrderNumber());
            }*/
            if (data.getStylistName() != null) {
                holder.employee.setText(data.getStylistName());
            }
            if (data.getClockin() != null) {
                holder.clockIn.setText(data.getClockin());
            }
          /*  if (data.getCouponNumber() != null) {
                holder.breakStart.setText(data.getCouponNumber());
            }
            if (data.getDiscountAmount() != null) {
                holder.breakEnd.setText(data.getDiscountAmount());
            }*/

            if (data.getClockout() != null) {
                holder.clockOut.setText(data.getClockout());
            }
            if (data.getEmergencyClock() != null) {
                holder.emergClockOut.setText(data.getEmergencyClock());
            }
            if (data.getSchFromTime() != null) {
                holder.scheduleForTime.setText(data.getSchFromTime());
            }

            if (data.getSchToTime() != null) {
                holder.scheduleToTime.setText(data.getSchToTime());
            }

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    employeeOnClick.EmployeeOne(view,i,list.get(i).getStylistId());
                 apiCommunicator.getApiData("stylist_id",list.get(i).getStylistId());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

class AttendanceListHolder extends RecyclerView.ViewHolder {
    TextView employee, clockIn, breakStart, breakEnd, clockOut, emergClockOut, scheduleForTime, scheduleToTime, workTime;
    LinearLayout mainLayout;

    public AttendanceListHolder(@NonNull View itemView) {
        super(itemView);
        mainLayout =itemView.findViewById(R.id.mainLayout);
        employee = itemView.findViewById(R.id.tvEmployee);
        clockIn = itemView.findViewById(R.id.tvClockIn);
        breakStart = itemView.findViewById(R.id.tvBreakStart);
        breakEnd = itemView.findViewById(R.id.tvBreakEnd);
        clockOut = itemView.findViewById(R.id.tvClockOut);
        emergClockOut = itemView.findViewById(R.id.tvEmergencyClockout);
        scheduleForTime = itemView.findViewById(R.id.tvScheduleForTime);
        scheduleToTime = itemView.findViewById(R.id.tvScheduleToTime);
        workTime = itemView.findViewById(R.id.tvWorkTime);

    }
}
