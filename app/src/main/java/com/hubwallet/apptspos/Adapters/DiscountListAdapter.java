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
import com.hubwallet.apptspos.Utils.Models.Attendance.AttendanceData;
import com.hubwallet.apptspos.Utils.Models.Discount.DiscountData;

import java.util.List;

public class DiscountListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DiscountData> list;
    private ApiCommunicator apiCommunicator;
    private int HEADER = 1111;
    private int CONTENT = 1231;

    public DiscountListAdapter(Context context, List<DiscountData> list, ApiCommunicator apiCommunicator) {
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
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_discount_list_header, null, false);
            v.setLayoutParams(lp);
            viewHolder = new DiscountListHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_discount_list, null, false);
            v.setLayoutParams(lp);
            viewHolder = new DiscountListHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == CONTENT) {
            final DiscountData data = list.get(i);
            DiscountListHolder holder = (DiscountListHolder) viewHolder;
            if (i %2==1){
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else {
                holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
            }
           /* holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiCommunicator.getApiData("get_order_detail", data.getOrderId());
                }
            });*/
           /* if (data.getAttendanceId() != null) {
                holder.oderNumber.setText(data.getOrderNumber());
            }*/
            if (data.getDiscountFor() != null) {
                holder.discountFor.setText(data.getDiscountFor());
            }
            if (data.getCouponType() != null) {
                holder.discountType.setText(data.getCouponType());
            }
          /*  if (data.getCouponNumber() != null) {
                holder.breakStart.setText(data.getCouponNumber());
            }
            if (data.getDiscountAmount() != null) {
                holder.breakEnd.setText(data.getDiscountAmount());
            }*/

            if (data.getDiscount() != null) {
                holder.discount.setText(data.getDiscount());
            }
            if (data.getStartDate() != null) {
                holder.startDate.setText(data.getStartDate());
            }
            if (data.getEndDate() != null) {
                holder.endDate.setText(data.getEndDate());
            }

            if (data.getMinAmount() != null) {
                holder.minSpentAmount.setText(data.getMinAmount());
            }

            if (data.getIsActive() != null) {
                holder.status.setText(data.getIsActive());
            }

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apiCommunicator.getApiData("discount_id",list.get(i).getCouponId());
                }
            });

           /* if (data.getPaymentDate() != null) {
                holder.workTime.setText(data.getPaymentDate());
            }
            if (data.getAppointmentId() == null || data.getAppointmentId().equalsIgnoreCase("")) {
                holder.services.setText("No");
            } else {
                holder.services.setText("YES");
            }*/
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

class DiscountListHolder extends RecyclerView.ViewHolder {
    TextView discountFor, discountType, discount, startDate, endDate, minSpentAmount, status;
    LinearLayout parentLayout;

    public DiscountListHolder(@NonNull View itemView) {
        super(itemView);
        parentLayout = itemView.findViewById(R.id.parentLayout);
        discountFor = itemView.findViewById(R.id.discountFor);
        discountType = itemView.findViewById(R.id.discountType);
        discount = itemView.findViewById(R.id.discount);
        startDate = itemView.findViewById(R.id.startDate);
        endDate = itemView.findViewById(R.id.endDate);
        minSpentAmount = itemView.findViewById(R.id.minSpentAmount);
        status = itemView.findViewById(R.id.status);
    }
}
