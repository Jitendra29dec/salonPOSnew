package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.APis.DeleteCart;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.CartModel.CartData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartProductAdapter extends RecyclerView.Adapter<productHolder> {
    private Context context;
    private List<CartData> list;
    private ApiCommunicator apiCommunicator;

    public cartProductAdapter(Context context, List<CartData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.apiCommunicator = apiCommunicator;
    }


    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_for_product_list_adapter, null, false);
        return new productHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull productHolder productHolder, int i) {
        productHolder.imageButton.setVisibility(View.VISIBLE);
        productHolder.productName.setText(list.get(i).getProductName());
        productHolder.productPrice.setText("$" + list.get(i).getPriceRetail());
        productHolder.quantitiy.setText(list.get(i).getQuantity());
        Glide.with(context).load(list.get(i).getProductImage()).into(productHolder.imageView);
        final int pos = i;
        productHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySingleton.getInstance(context).addToRequestQue(new DeleteCart(list.get(pos).getCartId(), apiCommunicator).getStringRequest());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

class productHolder extends RecyclerView.ViewHolder {
    ImageView imageButton;
    TextView productName, productPrice, quantitiy;
    ImageView imageView;


    productHolder(@NonNull View itemView) {
        super(itemView);
        imageButton = itemView.findViewById(R.id.closeButtonView);
        quantitiy = itemView.findViewById(R.id.quantityProductView);
        imageView = itemView.findViewById(R.id.productImageView);
        productName = itemView.findViewById(R.id.productNameView);
        productPrice = itemView.findViewById(R.id.productPriceView);
    }
}