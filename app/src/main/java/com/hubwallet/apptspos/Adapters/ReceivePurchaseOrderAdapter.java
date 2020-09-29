package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;

import java.util.List;

public class ReceivePurchaseOrderAdapter extends RecyclerView.Adapter<ReceivePurchaseOrderAdapter.ViewHolder> {

    private Context context;
    private List<BrandData> list;
    private ApiCommunicator apiCommunicator;

    public ReceivePurchaseOrderAdapter(Context context, List<BrandData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public ReceivePurchaseOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_receive_order_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivePurchaseOrderAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
