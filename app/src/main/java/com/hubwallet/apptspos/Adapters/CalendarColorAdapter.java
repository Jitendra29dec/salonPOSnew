package com.hubwallet.apptspos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.ColorPikInterface;
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceData;
import com.hubwallet.apptspos.Utils.Models.CalendarColorData;

import java.util.List;

public class CalendarColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CalendarColorData> list;
    private ApiCommunicator apiCommunicator;
    private int HEADER = 1111;
    private int CONTENT = 1231;
    private String colorCode;
    ColorPikInterface colorPikInterface;

    public CalendarColorAdapter(Context context, List<CalendarColorData> list, ApiCommunicator apiCommunicator,ColorPikInterface colorPikInterface) {
        this.context = context;
        this.list = list;
        this.list.add(0,null);
        this.apiCommunicator = apiCommunicator;
        this.colorPikInterface = colorPikInterface;
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
            View v = LayoutInflater.from(context).inflate(R.layout.item_calendar_color_header, null, false);
            v.setLayoutParams(lp);
            viewHolder = new CalendarColorListHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_calendar_color_list, null, false);
            v.setLayoutParams(lp);
            viewHolder = new CalendarColorListHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == CONTENT) {
            final CalendarColorData data = list.get(position);
            CalendarColorListHolder holder = (CalendarColorListHolder) viewHolder;
            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
            }
            holder.colorName.setText(data.getStatusName());
            holder.color.setBackgroundColor(Color.parseColor(data.getColorCode()));
            holder.textColor.setBackgroundColor(Color.parseColor(data.getTextColor()));

            holder.color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colorPikInterface.onClick(view,position);
                }
            });
            holder.textColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colorPikInterface.onClick(view,position);
                }
            });
            holder.submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colorPikInterface.updateColor(view,position,data.getColorId());
                }
            });

        }
    }

    public void setColorOnView(String colorCode, int position, int type) {
        this.colorCode = colorCode;
        if (type == 0) {
            list.get(position).setColorCode(colorCode);
        } else {
            list.get(position).setTextColor(colorCode);
        }
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }


}

class CalendarColorListHolder extends RecyclerView.ViewHolder {
    AppCompatTextView colorName, color, textColor,submitButton;

    public CalendarColorListHolder(@NonNull View itemView) {
        super(itemView);
        colorName = itemView.findViewById(R.id.name);
        color = itemView.findViewById(R.id.color);
        textColor = itemView.findViewById(R.id.textColor);
        submitButton = itemView.findViewById(R.id.submitButton);
    }
}
