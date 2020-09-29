package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.OrderDetailModel.ProductInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductDetailAdapter extends RecyclerView.Adapter {
    int HEADER = 123;
    int CONTENT = 124;
    private Context context;
    private List<ProductInfo> list;
    public ProductDetailAdapter(Context context, List<ProductInfo> list) {
        this.context = context;
        this.list = list;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);

        if (i == HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_for_adapter_product_detail_headinh, null, false);
            view.setLayoutParams(lp);
            viewHolder = new ProducDetailHeadingHolder(view);
        }
        if (i == CONTENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_for_adapter_product_detail, null, false);
            view.setLayoutParams(lp);
            viewHolder = new ProductDetailContentHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == CONTENT) {
            ProductDetailContentHolder holder = (ProductDetailContentHolder) viewHolder;
            ProductInfo obj = list.get(i - 1);
            holder.prodctName.setText(obj.getName());
            holder.productPrice.setText("$" + obj.getPrice());
            holder.productQuantity.setText(obj.getQuantity());
          //  holder.productTotal.setText("$" + (Float.parseFloat(obj.getQuantity()) * Float.parseFloat(obj.getPrice())) + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }
}

class ProducDetailHeadingHolder extends RecyclerView.ViewHolder {
    public ProducDetailHeadingHolder(@NonNull View itemView) {
        super(itemView);
    }
}

class ProductDetailContentHolder extends RecyclerView.ViewHolder {
    TextView prodctName, productQuantity, productPrice, productTotal;

    ProductDetailContentHolder(@NonNull View itemView) {
        super(itemView);
        prodctName = itemView.findViewById(R.id.productNameProductInfo);
        productPrice = itemView.findViewById(R.id.productPriceProductInfo);
        productQuantity = itemView.findViewById(R.id.productQuantityProductInfo);
        productTotal = itemView.findViewById(R.id.productTotalProductInfo);
    }
}