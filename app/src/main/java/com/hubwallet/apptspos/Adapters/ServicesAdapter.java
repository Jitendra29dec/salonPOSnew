package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.CheckoutServicesModel.CheckoutServiceData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter<serviceHolder> {
    private Context context;
    private List<CheckoutServiceData> list;

    public ServicesAdapter(Context context, List<CheckoutServiceData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public serviceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_for_services, null, false);
        return new serviceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull serviceHolder serviceHolder, int i) {
        serviceHolder.serviceText.setText(list.get(i).getServiceName());
        serviceHolder.stylistText.setText(list.get(i).getStylistName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class serviceHolder extends RecyclerView.ViewHolder {
    TextView serviceText;
    TextView stylistText;

    serviceHolder(@NonNull View itemView) {
        super(itemView);
        serviceText = itemView.findViewById(R.id.serviceTextView);
        stylistText = itemView.findViewById(R.id.stylistNameTextView);
    }
}