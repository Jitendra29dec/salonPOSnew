package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hubwallet.apptspos.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private String type;
    private int count = 4;

    public AdapterCategory(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_for_category, viewGroup, false);


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
