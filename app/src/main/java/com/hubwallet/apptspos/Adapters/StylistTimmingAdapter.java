package com.hubwallet.apptspos.Adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.GetStylistByIdModel.Schedule;
import com.hubwallet.apptspos.Utils.Models.StylistWeekSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StylistTimmingAdapter extends RecyclerView.Adapter {
    int HEADER = 101;
    int CONTENT = 102;
    ArrayList<StylistWeekSchedule> schedule;
    private Context context;
    private List<Schedule> list;
    private String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public StylistTimmingAdapter(Context context, List<Schedule> list) {
        this.context = context;
        this.list = list;
        schedule = new ArrayList<>();
        if (list == null) {
            for (String day : days) {
                schedule.add(new StylistWeekSchedule(day, "09:00AM", "10:00PM", true));
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getActive().equalsIgnoreCase("true")) {
                    schedule.add(new StylistWeekSchedule(days[i], list.get(i).getFromTime(), list.get(i).getToTime(), true));
                } else {
                    schedule.add(new StylistWeekSchedule(days[i], "00.00AM", "00.00AM", false));

                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
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
            View v = LayoutInflater.from(context).inflate(R.layout.stylist_timimg_header, null, false);
            v.setLayoutParams(lp);
            viewHolder = new HeaderHolder(v);
        }
        if (i == CONTENT) {
            View v = LayoutInflater.from(context).inflate(R.layout.stylist_timming_content, null, false);
            v.setLayoutParams(lp);
            viewHolder = new ContentStylistHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final int position = i - 1;
        if (getItemViewType(i) == CONTENT) {
            final ContentStylistHolder holder = (ContentStylistHolder) viewHolder;
            holder.day.setText(schedule.get(i - 1).getDay());
            holder.from.setText(schedule.get(position).getFrom());
            holder.to.setText(schedule.get(position).getTo());
            holder.from.setOnClickListener(new View.OnClickListener() {
                Calendar calendar = Calendar.getInstance();

                @Override
                public void onClick(View v) {
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            cal.set(Calendar.MINUTE, minute);
                            Format formatter;
                            formatter = new SimpleDateFormat("h:mm a");
                            String time = formatter.format(cal.getTime());
                            holder.from.setText(time);
                            schedule.get(position).setFrom(time);
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            });
            holder.isOnThatDay.setChecked(schedule.get(position).isOn());
            holder.isOnThatDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    schedule.get(position).setOnOrOff(isChecked);
                }
            });
            holder.to.setOnClickListener(new View.OnClickListener() {
                Calendar calendar = Calendar.getInstance();

                @Override
                public void onClick(View v) {
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            cal.set(Calendar.MINUTE, minute);
                            Format formatter;
                            formatter = new SimpleDateFormat("h:mm a");
                            String time = formatter.format(cal.getTime());
                            holder.to.setText(time);
                            schedule.get(position).setTo(time);
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public String getWeekSchedule() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < schedule.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                if (schedule.get(i).isOn()) {

                    object.put("day", days[i]);
                    object.put("active", true);
                    object.put("from", schedule.get(i).getFrom());
                    object.put("to", schedule.get(i).getTo());
                } else {
                    object.put("day", days[i]);
                    object.put("active", false);
                    object.put("from", "");
                    object.put("to", "");

                }
                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return jsonArray.toString();
    }
}

class HeaderHolder extends RecyclerView.ViewHolder {
    HeaderHolder(@NonNull View itemView) {
        super(itemView);
    }
}

class ContentStylistHolder extends RecyclerView.ViewHolder {
    TextView day, from, to;
    CheckBox isOnThatDay;

    ContentStylistHolder(@NonNull View itemView) {
        super(itemView);

        isOnThatDay = itemView.findViewById(R.id.checkboxStylistTiming);
        day = itemView.findViewById(R.id.dayTextViewStylistTiming);
        from = itemView.findViewById(R.id.fromTextViewStylistTiming);
        to = itemView.findViewById(R.id.toTextViewStylistTiming);
    }
}