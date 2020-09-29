package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.APis.AddToCart;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.ProductModel.ProductData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ProductListAdapter extends RecyclerView.Adapter<itemHolder> {
    private Context context;
    private List<ProductData> list;
    private String productName, quntity;

    public ProductListAdapter(Context context, List<ProductData> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View V = LayoutInflater.from(context).inflate(R.layout.item_for_product_list_adapter, null, false);
        return new itemHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull final itemHolder itemHolder, int i) {
        itemHolder.productPrice.setText("$" + list.get(i).getPriceRetail());
        itemHolder.productName.setText(list.get(i).getProductName());
        Glide.with(context).load(list.get(i).getMainImage()).into(itemHolder.imageView);
        itemHolder.quantitiy.setText("0");
        final int pos = i;
        itemHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = list.get(pos).getQuantity();
                count++;
                list.get(pos).setQuantity(count);
                itemHolder.quantitiy.setText(count + "");
            }
        });
    }

    public void getItemsArray() {
        ArrayList<String> produ = new ArrayList<>();
        ArrayList<String> qun = new ArrayList<>();
        for (ProductData productData : list) {
            if (productData.getQuantity() > 0) {
                produ.add(productData.getProductId());
                qun.add(productData.getQuantity() + "");
            }
        }
        productName = produ.toString();
        quntity = qun.toString();
        productName = productName.replace("[", "");
        productName = productName.replace("]", "");
        quntity = quntity.replace("[", "");
        quntity = quntity.replace("]", "");


    }

    public void update(String id, ApiCommunicator apiCommunicator, boolean isCheckout) {
        if (isCheckout) {
            MySingleton.getInstance(context).addToRequestQue(new AddToCart(productName, quntity, id, "0", apiCommunicator).getStringRequest());
        } else {
            MySingleton.getInstance(context).addToRequestQue(new AddToCart(productName, quntity, "0", id, apiCommunicator).getStringRequest());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class itemHolder extends RecyclerView.ViewHolder {
    TextView productName, productPrice, quantitiy;
    ImageView imageView;
    LinearLayout linearLayout;

    itemHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.mainLayoutProduct);
        quantitiy = itemView.findViewById(R.id.quantityProductView);
        imageView = itemView.findViewById(R.id.productImageView);
        productName = itemView.findViewById(R.id.productNameView);
        productPrice = itemView.findViewById(R.id.productPriceView);
    }
}