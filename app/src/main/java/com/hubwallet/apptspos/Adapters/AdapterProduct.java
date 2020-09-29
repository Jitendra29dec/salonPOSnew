package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hubwallet.apptspos.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private String type;
    private int count = 2;

    public AdapterProduct(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = null;
        if (type.equalsIgnoreCase("l")) {
            v = LayoutInflater.from(context).inflate(R.layout.item_for_product, null, false);

        } else if (type.equalsIgnoreCase("checkBox")) {
            v = LayoutInflater.from(context).inflate(R.layout.item_for_category, null, false);

        } else {
            v = LayoutInflater.from(context).inflate(R.layout.item_for_discount, null, false);
        }

        return new RecyclerView.ViewHolder(v) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void count() {
        count++;
        notifyDataSetChanged();
    }
}
