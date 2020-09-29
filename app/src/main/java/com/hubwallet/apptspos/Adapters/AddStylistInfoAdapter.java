package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddStylistInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<GetServicesData> list;
    private int HEADER = 101;
    private int CONTENT = 102;

    public AddStylistInfoAdapter(Context context, List<GetServicesData> list) {
        this.context = context;
        this.list = list;
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
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        RecyclerView.ViewHolder holder = null;
        if (i == HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.stylist_header, null, false);
            v.setLayoutParams(layoutParams);
            holder = new Holder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.stylist_content, null, false);
            v.setLayoutParams(layoutParams);
            holder = new contntHolder(v);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder itemHolder, final int i) {
        if (getItemViewType(i) == CONTENT) {
            final int pos = i;
            contntHolder contentHolder = (contntHolder) itemHolder;
            contentHolder.serviceNAme.setText(list.get(i - 1).getServiceName());
            contentHolder.checkBox.setChecked(list.get(i - 1).isChecked());
            contentHolder.price.setText(list.get(i - 1).getPrice());
            contentHolder.duration.setText(list.get(i - 1).getDuration());
            contentHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(i - 1).setChecked(isChecked);
                }
            });
            contentHolder.duration.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    list.get(i - 1).setDuration(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            contentHolder.price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    list.get(i - 1).setPrice(s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public String getStylistSchedule() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                if (list.get(i).isChecked()) {
                    object.put("service_id", list.get(i).getServiceID());
                    object.put("duration", list.get(i).getDuration());
                    object.put("price", list.get(i).getPrice());
                    jsonArray.put(object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }
}

class Holder extends RecyclerView.ViewHolder {
    Holder(@NonNull View itemView) {
        super(itemView);
    }
}


class contntHolder extends RecyclerView.ViewHolder {
    TextView serviceNAme, price, duration;
    CheckBox checkBox;

    contntHolder(@NonNull View itemView) {
        super(itemView);
        serviceNAme = itemView.findViewById(R.id.servicesName);
        price = itemView.findViewById(R.id.priceTextViewStylist);
        duration = itemView.findViewById(R.id.durationTextViewStylist);
        checkBox = itemView.findViewById(R.id.stylistCheckbox);
    }
}