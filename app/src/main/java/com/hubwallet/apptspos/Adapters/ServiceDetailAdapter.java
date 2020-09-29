package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.OrderDetailModel.AppointmentData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceDetailAdapter extends RecyclerView.Adapter {
    int HEADER = 123;
    int CONTENT = 124;
    private Context context;
    private List<AppointmentData> list;
    public ServiceDetailAdapter(Context context, List<AppointmentData> list) {
        this.context = context;
        this.list = list;
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_for_adapter_service_detail, null, false);
            view.setLayoutParams(lp);
            viewHolder = new ServiceDetailHeadingHolder(view);
        }
        if (i == CONTENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_for_adapter_service_detail_content, null, false);
            view.setLayoutParams(lp);
            viewHolder = new ServiceDetailContentHolder(view);

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == CONTENT) {
            ServiceDetailContentHolder holder = (ServiceDetailContentHolder) viewHolder;
            AppointmentData obj = list.get(i - 1);
            holder.price.setText(obj.getPrice());
            holder.id.setText(obj.getAppointmentId());
            holder.service.setText(obj.getServiceName());
            holder.duration.setText(obj.getDuration());
            holder.stylist.setText(obj.getStylistName());
            holder.date.setText(obj.getApppointmentDate());

        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }
}

class ServiceDetailHeadingHolder extends RecyclerView.ViewHolder {
    public ServiceDetailHeadingHolder(@NonNull View itemView) {
        super(itemView);
    }
}

class ServiceDetailContentHolder extends RecyclerView.ViewHolder {
    TextView id, date, stylist, service, duration, price;

    ServiceDetailContentHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.appointmentIDAppointmentInfo);
        stylist = itemView.findViewById(R.id.appointmentStylistAppointmentInfo);
        date = itemView.findViewById(R.id.appointmentDateAppointmentInfo);
        service = itemView.findViewById(R.id.appointmentServiceAppointmentInfo);
        duration = itemView.findViewById(R.id.appointmentDurationAppointmentInfo);
        price = itemView.findViewById(R.id.appointmentPriceAppointmentInfo);
    }
}