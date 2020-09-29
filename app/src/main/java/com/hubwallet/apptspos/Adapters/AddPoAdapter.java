package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetProductById;
import com.hubwallet.apptspos.APis.GetProductLList;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.ItemClick;
import com.hubwallet.apptspos.Utils.Models.AddPoModel;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.CouponById.GetCouponData;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class AddPoAdapter extends RecyclerView.Adapter<AddPoAdapter.ViewHolder>  {
    private Context context;
    List<AddPoModel> list;
    private ApiCommunicator apiCommunicator;
    private GetProductData productData;
    private ItemClick itemClick;
    private List<GetProductData> productList;;

    public AddPoAdapter(Context context, List<AddPoModel> mProductDetailList,  List<GetProductData> productList,ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = mProductDetailList;
        this.productList = productList;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public AddPoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_po, null, false);
        view.setLayoutParams(lp);
        return new AddPoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddPoAdapter.ViewHolder holder, int position) {
        AddPoModel addPoModel = list.get(position);
        setSpinnerBrand(productList,holder.productSpinner);
      //  setSpinnerBrand((ArrayList<String>) list.get(position).getProduct(),holder.productSpinner);
        if (addPoModel.getRate()!=null){
            holder.rate.setText(addPoModel.getRate());
        }
        if (addPoModel.getQuantity()!=null){
            holder.quantity.setText(addPoModel.getQuantity());
        }
       /* holder.quantity.setText("1");
        holder.tax.setText("52");
        holder.amount.setText("20");*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateProductItem(AddPoModel addPoModel) {
        list.add(addPoModel);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Spinner productSpinner;
        private EditText quantity, rate, tax, amount;
        private int spinnerSelectedPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productSpinner = itemView.findViewById(R.id.productSpn);
            quantity = itemView.findViewById(R.id.quantity);
            rate = itemView.findViewById(R.id.rate);
            tax = itemView.findViewById(R.id.tax);
            amount = itemView.findViewById(R.id.amount);

            productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                   // itemClick.onItemClick(view,position);
                    String spinnerSelectedItem = productSpinner.getSelectedItem().toString();
                    MySingleton.getInstance(context).addToRequestQue(new GetProductById("70",new PrefManager(context).getVendorId(),apiCommunicator).getStringRequest());
                    notifyItemChanged(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }

    private void setSpinnerBrand(List<GetProductData> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(context, R.layout.li_spin_search, list));
    }

    public void updatefield(GetProductData data){
        this.productData = data;
       // list.get(1).setQuantity(data.getQuantity());

    }

}
