package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.GetPurchaseOrderData;

import java.util.List;

public class PurchaseOrderAdapter extends RecyclerView.Adapter<PurchaseOrderAdapter.ViewHolder> {
    private Context context;
    private List<GetPurchaseOrderData> list;
    private ApiCommunicator apiCommunicator;

    public PurchaseOrderAdapter(Context context, List<GetPurchaseOrderData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.apiCommunicator = apiCommunicator;
    }
    @NonNull
    @Override
    public PurchaseOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.item_purchase_order_list, null, false);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseOrderAdapter.ViewHolder holder, int position) {
        GetPurchaseOrderData model = list.get(position);
        if (position %2==1){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
        }
        holder.poNumber.setText(model.getPoNumber());
        holder.supplierName.setText(model.getSupplierName());
        holder.poDate.setText(model.getPoDate());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCommunicator.getApiData("po_id", list.get(position).getPoId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView poNumber,supplierName,poDate,editButton;
        LinearLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         //   editButton = itemView.findViewById(R.id.editButton);
            mainLayout = itemView.findViewById(R.id.parentLayout);
            poNumber= itemView.findViewById(R.id.poNumber);
            supplierName = itemView.findViewById(R.id.supplierName);
            poDate = itemView.findViewById(R.id.poDate);
        }
    }
}
