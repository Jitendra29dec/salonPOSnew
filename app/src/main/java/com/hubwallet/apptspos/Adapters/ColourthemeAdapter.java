package com.hubwallet.apptspos.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.ColorPikInterface;
import com.hubwallet.apptspos.Utils.CommanInterface;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategoryData;

import java.util.ArrayList;
import java.util.List;

public class ColourthemeAdapter extends RecyclerView.Adapter<ColourthemeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Integer> list;
    ColorPikInterface colorPikInterface;

    public ColourthemeAdapter(Context context, ArrayList<Integer> list,ColorPikInterface colorPikInterface) {
        this.context = context;
        this.list = list;
        this.colorPikInterface = colorPikInterface;
    }

    @NonNull
    @Override
    public ColourthemeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_clour_list, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ColourthemeAdapter.ViewHolder holder, int position) {
     holder.colorItem.setBackgroundColor(list.get(position));
     holder.colorItem.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             colorPikInterface.onClick(view,position);
         }
     });
     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button colorItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorItem = itemView.findViewById(R.id.colorItem);
        }
    }

}
