package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.SettingEmpData;

import java.util.List;

public class PermissionListAdapter extends RecyclerView.Adapter<PermissionListAdapter.ViewHolder>  {
    private Context context;
    private List<SettingEmpData> list;
    private List<SettingEmpData> filterList;
    private ApiCommunicator apiCommunicator;

    public PermissionListAdapter(Context context, List<SettingEmpData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        filterList = list;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_permission_list, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(filterList.get(position).getStylistName());

      /*  holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCommunicator.getApiData("brand_id", filterList.get(position).getBrandId());
            }
        });*/
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
            //   linearLayout = itemView.findViewById(R.id.mainLayoutProduct);
            name = itemView.findViewById(R.id.permissionName);
        }
    }
}
