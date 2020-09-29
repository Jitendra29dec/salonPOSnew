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

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable {
    private final Context context;
    private final List<GetProductData> list;
    private List<GetProductData> filterList;
    private ApiCommunicator apiCommunicator;

    public ProductsAdapter(Context context, List<GetProductData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.filterList = list;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_for_product_list_adapter, null, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, final int i) {
        productViewHolder.name.setText(filterList.get(i).getProductName());
        if (filterList.get(i).getProductImage() != null
                && !filterList.get(i).getProductImage().isEmpty()) {
            Glide.with(context)
                    .load(filterList.get(i).getProductImage())
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(productViewHolder.image);
        } else {
            Glide.with(context)
                    .load(R.drawable.place_holder_1)
                    .into(productViewHolder.image);
        }

      //  productViewHolder.qty.setVisibility(View.VISIBLE);
        productViewHolder.qty.setText(filterList.get(i).getQuantity() + " " +"Unit");
        productViewHolder.price.setText(String.format("$ %s", filterList.get(i).getPriceRetail()));
        productViewHolder.mobile.setText(filterList.get(i).getSku());
       // productViewHolder.price.setText(filterList.get(i).getPriceRetail());

//        productViewHolder.closeButton.setVisibility(View.GONE);
//        final int pos = i;
//        productViewHolder.closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                alertDialog.setMessage("Are you sure?");
//                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        MySingleton.getInstance(context).addToRequestQue(new DeleteProduct(list.get(pos).getProductId(), apiCommunicator).getStringRequest());
//
//                    }
//                });
//                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.show();
//            }
//
//        });

        productViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCommunicator.getApiData("product_id", filterList.get(i).getProductId());
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
                List<GetProductData> tempList = new ArrayList<>();
                if (constraint.toString().equalsIgnoreCase("")) {
                    tempList = list;
                } else {
                    for (GetProductData data : list) {
                        if (data.getProductName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                    filterList = (List<GetProductData>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}

class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView name, price, qty,mobile;
    ImageView closeButton, image;
    LinearLayout linearLayout;

    ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.mainLayoutProduct);
        name = itemView.findViewById(R.id.productNameView);
        image = itemView.findViewById(R.id.productImageView);
        price = itemView.findViewById(R.id.productPriceView);
        qty = itemView.findViewById(R.id.quantityProductView);
        mobile = itemView.findViewById(R.id.mobileEdit);
        closeButton = itemView.findViewById(R.id.closeButtonView);
    }
}