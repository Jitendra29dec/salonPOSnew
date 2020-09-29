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
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierData;

import java.util.ArrayList;
import java.util.List;

public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<SupplierData> list;
    private List<SupplierData> filterList;
    private ApiCommunicator apiCommunicator;

    public VendorListAdapter(Context context, List<SupplierData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        filterList = list;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public VendorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categeory_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorListAdapter.ViewHolder holder, int position) {
        holder.name.setText(filterList.get(position).getSupplierName());
        holder.phone.setText(filterList.get(position).getPhone());
        if (filterList.get(position).getPhoto() != null
                && !filterList.get(position).getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(filterList.get(position).getPhoto())
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(holder.image);
        } else {
            Glide.with(context)
                    .load(R.drawable.place_holder_1)
                    .into(holder.image);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCommunicator.getApiData("supplier_id", filterList.get(position).getSupplierId());
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
                List<SupplierData> tempList = new ArrayList<>();
                if (constraint.toString().equalsIgnoreCase("")) {
                    tempList = list;
                } else {
                    for (SupplierData data : list) {
                        if (data.getSupplierName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                    filterList = (List<SupplierData>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView name,phone;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.mainLayoutProduct);
            name = itemView.findViewById(R.id.categoryNameView);
            image = itemView.findViewById(R.id.categoryImageView);
            phone= itemView.findViewById(R.id.desProductView);
        }
    }
}
