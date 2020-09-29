package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.Coupon.CouponData;

import java.util.List;

public class CouponListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CouponData> list;
    private ApiCommunicator apiCommunicator;
    private int HEADER = 1111;
    private int CONTENT = 1231;

    public CouponListAdapter(Context context, List<CouponData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.list.add(0,null);
        this.apiCommunicator = apiCommunicator;
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
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_coupon_list_header, null, false);
            v.setLayoutParams(lp);
            viewHolder = new CouponListHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_coupon_list, null, false);
            v.setLayoutParams(lp);
            viewHolder = new CouponListHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == CONTENT) {
            final CouponData data = list.get(i);
            CouponListHolder holder = (CouponListHolder) viewHolder;
            if (i %2==1){
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else {
                holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
            }
            if (data.getCouponCode()!= null){
                holder.couponCode.setText(data.getCouponCode());
            }
            if (data.getCouponFor() != null) {
                holder.couponFor.setText(data.getCouponFor());
            }
            if (data.getDiscountType() != null) {
                holder.discountType.setText(data.getDiscountType());
            }

            if (data.getDiscount() != null) {
                holder.discount.setText(data.getDiscount());
            }
            if (data.getStartDate() != null) {
                holder.validFrom.setText(data.getStartDate());
            }
            if (data.getEndDate() != null) {
                holder.validTill.setText(data.getEndDate());
            }

            if (data.getStatus() != null) {
                holder.status.setText(data.getStatus());
            }
           /* if (data.getPaymentDate() != null) {
                holder.workTime.setText(data.getPaymentDate());
            }
            if (data.getAppointmentId() == null || data.getAppointmentId().equalsIgnoreCase("")) {
                holder.services.setText("No");
            } else {
                holder.services.setText("YES");
            }*/

           holder.parentLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   apiCommunicator.getApiData("coupon_id", list.get(i).getCouponId());
               }
           });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

class CouponListHolder extends RecyclerView.ViewHolder {
    TextView couponCode, couponFor, discountType, discount, validFrom, validTill, status;
    LinearLayout parentLayout;

    public CouponListHolder(@NonNull View itemView) {
        super(itemView);
        couponCode = itemView.findViewById(R.id.couponCode);
        couponFor = itemView.findViewById(R.id.couponFor);
        discount = itemView.findViewById(R.id.discount);
        discountType = itemView.findViewById(R.id.discountType);
        validFrom = itemView.findViewById(R.id.validFrom);
        validTill = itemView.findViewById(R.id.validTill);
        status = itemView.findViewById(R.id.status);
        parentLayout = itemView.findViewById(R.id.parentLayout);

    }
}
