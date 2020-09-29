package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategoryData;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> implements Filterable {
    private  Context context;
    private  List<ProductCategoryData> list;
    private List<ProductCategoryData> filterList;
    private ApiCommunicator apiCommunicator;

    public CategoryListAdapter(Context context, List<ProductCategoryData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        filterList = list;
        this.apiCommunicator = apiCommunicator;
    }
    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_categeory_list, null, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        holder.name.setText(filterList.get(position).getCategoryName());
        holder.descreption.setText(filterList.get(position).getDescription());
        Glide.with(context)
                .load(R.drawable.place_holder_1)
                .into(holder.image);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCommunicator.getApiData("category_id", filterList.get(position).getCategoryId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ProductCategoryData> tempList = new ArrayList<>();
                if (constraint.toString().equalsIgnoreCase("")) {
                    tempList = list;
                } else {
                    for (ProductCategoryData data : list) {
                        if (data.getCategoryName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(data);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.count = tempList.size();
                results.values = tempList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null) {
                    filterList = (List<ProductCategoryData>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView name,descreption;
        ImageView  image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.mainLayoutProduct);
            name = itemView.findViewById(R.id.categoryNameView);
            descreption = itemView.findViewById(R.id.desProductView);
            image = itemView.findViewById(R.id.categoryImageView);
        }
    }
}
