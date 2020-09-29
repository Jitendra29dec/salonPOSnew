package com.hubwallet.apptspos.Adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData;
import com.hubwallet.apptspos.Utils.Models.GroupAppointmentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  List<GetStylistData> stylistList;
    private  List<GetServicesData> servicList;
    private int HEADING = 1001;
    private int ADDMORE = 1006;
    private int CONTENT = 1002;
    private List<GroupAppointmentModel> list;
    private String time;
    private String date;
    private List<String> serviceNames;
    private List<String> stylistName;
    private Context context;
    private String[] value;

    public GroupServiceAdapter(int i, List<List<GroupAppointmentModel>> appointmentList,
                               List<GetStylistData> stylistList,
                               List<GetServicesData> servicList) {
        this.list = appointmentList.get(i);
        this.time = this.list.get(0).getTime();
        this.date = this.list.get(0).getDate();
        this.serviceNames = new ArrayList<>();
        this.stylistName = new ArrayList<>();
        this.stylistList = stylistList;
        this.servicList = servicList;
        serviceNames.add("--Select--");
        stylistName.add("--Select--");
        for (GetStylistData x :
                stylistList) {
            stylistName.add(x.getStylistName());
        }
        for (GetServicesData x : servicList) {
            serviceNames.add(x.getServiceName());
        }




    }

    public GroupServiceAdapter(List<GroupAppointmentModel> appointmentList, List<GetStylistData> stylistList,
                               List<GetServicesData> servicList){
        this.list = appointmentList;
        this.date =appointmentList.get(0).getDate();
        this.time =appointmentList.get(0).getTime();
        this.stylistList = stylistList;
        this.servicList = servicList;
        this.serviceNames = new ArrayList<>();
        this.stylistName = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        int id = 0;
//        if (position == 0) {
//            id = HEADING;
//        } else
//        if (position == getItemCount() - 1) {
//            id = ADDMORE;
//        } else {
        id = CONTENT;
//        }
        return id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        context = parent.getContext();
//        if (viewType == HEADING) {
//            view = LayoutInflater.from(context).inflate(R.layout.service_group_layout, parent, false);
//            viewHolder = new HeadingHolder(view);
//        }
        if (viewType == CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.service_group_content, parent, false);
            viewHolder = new ContentHolder(view);
        }
//        if (viewType == ADDMORE) {
//            view = LayoutInflater.from(context).inflate(R.layout.add_more_group, parent, false);
//            viewHolder = new AddMoreHolder(view);
//        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == CONTENT) {
            final ContentHolder holder1 = (ContentHolder) holder;
            /* position - 1
             * changed to
             * position
             * */
            holder1.dateTextView.setText(list.get(position).getDate());
            holder1.timeTextView.setText(list.get(position).getTime());
            holder1.durationTextView.setText(list.get(position).getDuration());
            holder1.priceTextView.setText(list.get(position).getPrice());

            if (position == 0) {
//                holder1.delte.setVisibility(View.INVISIBLE);
                holder1.delte.setImageResource(R.drawable.ic_circle_add_blue);
            } else {
                holder1.delte.setImageResource(R.drawable.ic_circle_close_red);
            }



            holder1.delte.setOnClickListener(v -> {
                if (position == 0) {
                    if (!list.get(position).getStylist().equalsIgnoreCase("") && !list.get(position).getService().equalsIgnoreCase("")) {
                        list.add(new GroupAppointmentModel(date, time, "", "", "0", "0"));
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Select Service And Stylist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            final String[] val = date.split("-");
            holder1.dateTextView.setOnClickListener(v -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        StringBuilder builder = new StringBuilder().append(year).append("-").append(getDoubleDigit(month + 1)).append("-").append(dayOfMonth);
                        holder1.dateTextView.setText(builder.toString());
                        list.get(position).setDate(builder.toString());
                    }
                }, Integer.parseInt(val[0]), Integer.parseInt(val[1]) - 1, Integer.parseInt(val[2]));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            });
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
            final SimpleDateFormat output = new SimpleDateFormat("HH:mm", Locale.US);
            String timwe24hour = null;
            try {
                Date date = simpleDateFormat.parse(list.get(position).getTime());
                timwe24hour = output.format(date);
                value = timwe24hour.split(":");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder1.timeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String time = hourOfDay + ":" + minute;
                            try {
                                Date date = output.parse(time);
                                holder1.timeTextView.setText(simpleDateFormat.format(date));
                                list.get(position).setTime(simpleDateFormat.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, Integer.parseInt(value[0]), Integer.parseInt(value[1].replace(" ", "")), true);
                    timePickerDialog.show();
                }
            });

            holder1.stylistSpinner.setAdapter(new ArrayAdapter<>(context, R.layout.li_spin_search, stylistName));
            holder1.serviceSpinner.setAdapter(new ArrayAdapter<>(context, R.layout.li_spin_search, serviceNames));

            holder1.serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                    if (p != 0) {
                        list.get(position).setService(servicList.get(p - 1).getServiceID());
                        holder1.priceTextView.setText(servicList.get(p - 1).getPrice());
                        holder1.durationTextView.setText(servicList.get(p - 1).getDuration());//+1 because select is at first
                        list.get(position).setDuration(servicList.get(p - 1).getDuration());//+1 because select is at first
                        holder1.stylistSpinner.setBackground(context.getDrawable(R.drawable.spinner_bg_red));

                    } else {
                        holder1.priceTextView.setText("0");
                        holder1.durationTextView.setText("0");//+1 because select is at first
                        holder1.stylistSpinner.setBackground(context.getDrawable(R.drawable.spinner_bg));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            holder1.stylistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if (pos != 0) {
                        list.get(position).setStylist(stylistList.get(pos - 1).getStylistId());
                        holder1.stylistSpinner.setBackground(context.getDrawable(R.drawable.spinner_bg));
                    } else if (holder1.serviceSpinner.getSelectedItemPosition() != 0)
                        holder1.stylistSpinner.setBackground(context.getDrawable(R.drawable.spinner_bg_red));

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            int posService = -1, poStylist = -1;
            if (list.get(position).getService() != null) {
                for (int i = 0; i < servicList.size(); i++) {
                    GetServicesData data = servicList.get(i);
                    if (data.getServiceID().equals(list.get(position).getService())) {
                        posService = i + 1;
                    }
                }
            }
            if (posService != -1)
                holder1.serviceSpinner.setSelection(posService);

            if (list.get(position).getStylist() != null) {
                for (int i = 0; i < stylistList.size(); i++) {
                    GetStylistData data = stylistList.get(i);
                    if (data.getStylistId().equals(list.get(position).getStylist())) {
                        poStylist = i + 1;
                    }
                }
            }
            if (poStylist != -1)
                holder1.stylistSpinner.setSelection(poStylist);
            if (list.size() >= 1 && position >= 1) {

                if (!list.get(position - 1).getDuration().equalsIgnoreCase("0")) {//position-2 for getting previous item
                    try {
                        Date date = simpleDateFormat.parse(list.get(position - 1).getTime());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int rem = 0;
                        int min = Integer.parseInt(list.get(position - 1).getDuration().replace(" ", "")) + 1;
                        int hour = min / 60;
                        rem = min % 60;
                        calendar.add(Calendar.HOUR, hour);
                        calendar.add(Calendar.MINUTE, rem);
                        Log.e("onBindViewHolder: ", simpleDateFormat.format(calendar.getTime()) + "##3" + hour + "  " + rem);
                        list.get(position).setTime(simpleDateFormat.format(calendar.getTime()));
                        holder1.timeTextView.setText(simpleDateFormat.format(calendar.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
//        if (getItemViewType(position) == ADDMORE) {
//            AddMoreHolder addMoreHolder = (AddMoreHolder) holder;
//            addMoreHolder.note.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final EditText noteView;
//                    Button submit;
//                    final AlertDialog dialog = new AlertDialog.Builder(context).create();
//                    View view = LayoutInflater.from(context).inflate(R.layout.note_view_appointment_group, null, false);
//                    dialog.setView(view);
//                    noteView = view.findViewById(R.id.noteEditTextGroupAlert);
//                    submit = view.findViewById(R.id.submitButtonGroupAlert);
//                    if (list.get(0).getNote() != null && !list.get(0).getNote().equalsIgnoreCase("")) {
//                        noteView.setText(list.get(0).getNote());
//                    }
//                    submit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            list.get(0).setNote(noteView.getText().toString());
//                            dialog.dismiss();
//                            Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    dialog.show();
//                }
//            });
//            addMoreHolder.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!list.get(position - 1).getStylist().equalsIgnoreCase("") && !list.get(position - 1).getService().equalsIgnoreCase("")) {
//                        list.add(new GroupAppointmentModel(date, time, "", "", "0", "0"));
//                        notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(context, "Select Service And Stylist", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    String getDoubleDigit(int val) {
        if (val <= 9) {
            return "0" + val;
        }
        return val + "";
    }
}

class HeadingHolder extends RecyclerView.ViewHolder {
    HeadingHolder(@NonNull View itemView) {
        super(itemView);
    }
}

class ContentHolder extends RecyclerView.ViewHolder {
    TextView dateTextView, timeTextView, durationTextView, priceTextView;
    ImageView delte;
    Spinner serviceSpinner, stylistSpinner;
//    LinearLayout linearLayout;

    ContentHolder(@NonNull View itemView) {
        super(itemView);
        dateTextView = itemView.findViewById(R.id.dateTextViewGroup);
        timeTextView = itemView.findViewById(R.id.timeEditTextGroup);
        durationTextView = itemView.findViewById(R.id.durationTextViewGroup);
        priceTextView = itemView.findViewById(R.id.priceTextViewGroup);
        delte = itemView.findViewById(R.id.deleteGroupElement);
        serviceSpinner = itemView.findViewById(R.id.serviceSpinnerGroup);
        stylistSpinner = itemView.findViewById(R.id.stylistSpinnerGroup);
//        linearLayout = itemView.findViewById(R.id.stylistLinearLayoutGroup);
    }
}

class AddMoreHolder extends RecyclerView.ViewHolder {
    Button add, note;

    AddMoreHolder(@NonNull View itemView) {
        super(itemView);
        add = itemView.findViewById(R.id.addButtonGroupElement);
        note = itemView.findViewById(R.id.noteButtonGroupElement);

    }
}