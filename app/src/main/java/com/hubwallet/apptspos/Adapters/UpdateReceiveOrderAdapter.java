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

public class UpdateReceiveOrderAdapter extends RecyclerView.Adapter<UpdateReceiveOrderAdapter.ViewHolder>{
    private Context context;
    private List<BrandData> list;
    private ApiCommunicator apiCommunicator;

    public UpdateReceiveOrderAdapter(Context context, List<BrandData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.apiCommunicator = apiCommunicator;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_receive_order_update, null, false);
        return new UpdateReceiveOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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
