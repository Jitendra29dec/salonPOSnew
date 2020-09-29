package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.SettingEmpData;

import java.util.ArrayList;
import java.util.List;

public class EmpListAdapter extends RecyclerView.Adapter<EmpListAdapter.ViewHolder>  {
    private Context context;
    private List<SettingEmpData> list;
    private List<SettingEmpData> filterList;
    private ApiCommunicator apiCommunicator;

    public EmpListAdapter(Context context, List<SettingEmpData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        filterList = list;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.emp_list, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(filterList.get(position).getStylistName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCommunicator.getApiData("emp_id", filterList.get(position).getStylistId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.parentLayout);
            name = itemView.findViewById(R.id.empName);
        }
    }
}
