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
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;

import java.util.ArrayList;
import java.util.List;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> implements Filterable {
    private  Context context;
    private  List<GetServicesData> list;
    private List<GetServicesData> filterList;
    private ApiCommunicator apiCommunicator;

    public ServiceListAdapter(Context context, List<GetServicesData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.filterList = list;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_for_service_list_adapter, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(filterList.get(position).getCategoryName());
        holder.makUp.setText(filterList.get(position).getServiceName());
        /*if (filterList.get(position).getProductImage() != null
                && !filterList.get(position).getProductImage().isEmpty()) {
            Glide.with(context)
                    .load(filterList.get(position).getProductImage())
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(holder.image);
        } else {
            Glide.with(context)
                    .load(R.drawable.place_holder_1)
                    .into(holder.image);
        }*/
        Glide.with(context)
                .load(R.drawable.place_holder_1)
                .into(holder.image);

        holder.duration.setText(filterList.get(position).getDuration());
        holder.price.setText(String.format("$ %s", filterList.get(position).getPrice()));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCommunicator.getApiData("service_id", filterList.get(position).getServiceID());
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
                List<GetServicesData> tempList = new ArrayList<>();
                if (constraint.toString().equalsIgnoreCase("")) {
                    tempList = list;
                } else {
                    for (GetServicesData data : list) {
                        if (data.getServiceName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                    filterList = (List<GetServicesData>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView makUp, price, duration,status,categoryName;
        ImageView closeButton, image;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.mainLayoutProduct);
            categoryName = itemView.findViewById(R.id.makeUp);
            makUp = itemView.findViewById(R.id.serviceMakeup);
            image = itemView.findViewById(R.id.productImageView);
            price = itemView.findViewById(R.id.servicePrice);
            duration = itemView.findViewById(R.id.serviceDuration);
            status = itemView.findViewById(R.id.serviceStatus);
            closeButton = itemView.findViewById(R.id.closeButtonView);
        }
    }};

