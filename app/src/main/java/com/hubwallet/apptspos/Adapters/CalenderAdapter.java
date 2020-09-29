package com.hubwallet.apptspos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.CommanInterface;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.MyViewHolder> {
    private Context context;
    List<CalandarData> arrayListCallBack;
    private ApiCommunicator apiCommunicator;
    private CommanInterface commanInterface;
    public String time;

    public CalenderAdapter(Context activity, List<CalandarData> arrayListCallBack, ApiCommunicator apiCommunicator, CommanInterface commanInterface) {
        this.context = activity;
        this.arrayListCallBack = arrayListCallBack;
        this.apiCommunicator = apiCommunicator;
        this.commanInterface = commanInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textDays.setText(arrayListCallBack.get(position).getmDays());
        holder.textStartTime.setText(arrayListCallBack.get(position).getmStartTime());
        holder.textEndTime.setText(arrayListCallBack.get(position).getmEndTime());
        if (arrayListCallBack.get(position).getmSwitch().equalsIgnoreCase("0")) {
            holder.swOnOff.setChecked(false);
        } else {
            holder.swOnOff.setChecked(true);
        }
        holder.swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    arrayListCallBack.get(position).setmSwitch("1");
                }else {
                    arrayListCallBack.get(position).setmSwitch("0");

                }
            }
        });
      //  Log.d("StratTime", arrayListCallBack.get(position).getmStartTime());
      //  Log.d("EndTime", arrayListCallBack.get(position).getmEndTime());
        holder.textStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commanInterface.selectTime(view, position,arrayListCallBack);
            }
        });

        holder.textEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commanInterface.selectTime(view, position,arrayListCallBack);
            }
        });

    }

    public void setTimeOnView(String time, int position, int type) {
        this.time = time;
        if (type == 0) {
            arrayListCallBack.get(position).setmStartTime(time);
        } else {
            arrayListCallBack.get(position).setmEndTime(time);
        }
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return arrayListCallBack.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.switchBtn)
        SwitchCompat swOnOff;
        @BindView(R.id.dayNameTv)
        AppCompatTextView textDays;
        @BindView(R.id.fromTV)
        AppCompatTextView textStartTime;
        @BindView(R.id.toTV)
        AppCompatTextView textEndTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setCallInterface(CommanInterface commanInterface1) {
        this.commanInterface = commanInterface1;
    }

}
