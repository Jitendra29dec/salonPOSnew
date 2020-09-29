package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategoryData;

import java.util.ArrayList;
import java.util.List;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ProductCategoryData> list;
    private List<ProductCategoryData> filterList;
    private ApiCommunicator apiCommunicator;
    private int HEADER = 1111;
    private int CONTENT = 1231;

    public ServiceCategoryAdapter(Context context, List<ProductCategoryData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        filterList = list;
        this.apiCommunicator = apiCommunicator;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        if (viewType == HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_service_category_header, null, false);
            v.setLayoutParams(lp);
            viewHolder = new ServiceCategoryListHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_service_category_list, null, false);
            v.setLayoutParams(lp);
            viewHolder = new ServiceCategoryListHolder(v);
        }

        //  View v = LayoutInflater.from(context).inflate(R.layout.item_for_service_category_list, null, false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == CONTENT) {
            ServiceCategoryListHolder holder = (ServiceCategoryListHolder) viewHolder;
            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
            }
            holder.name.setText(filterList.get(position).getCategoryName());

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiCommunicator.getApiData("category_id", filterList.get(position).getCategoryId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }


    class ServiceCategoryListHolder extends RecyclerView.ViewHolder {
        AppCompatTextView edit;
        TextView name;

        public ServiceCategoryListHolder(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.submitButton);
            name = itemView.findViewById(R.id.categoryNameView);
        }
    }
}
