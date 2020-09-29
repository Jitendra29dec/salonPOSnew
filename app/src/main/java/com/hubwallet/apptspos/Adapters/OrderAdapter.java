package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GetOrderList.OrderData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<OrderData> list;
    private ApiCommunicator apiCommunicator;
    private int HEADER = 1111;
    private int CONTENT = 1231;
    public OrderAdapter(Context context, List<OrderData> list, ApiCommunicator apiCommunicator) {

        this.context = context;
        this.list = list;
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
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_order_list_header, null, false);
            v.setLayoutParams(lp);
            viewHolder = new OrderListHeaderHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_for_order_list_content, null, false);
            v.setLayoutParams(lp);
            viewHolder = new OrderListContentHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == CONTENT) {
            final OrderData data = list.get(i - 1);
            OrderListContentHolder holder = (OrderListContentHolder) viewHolder;
            holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiCommunicator.getApiData("get_order_detail", data.getOrderId());
                }
            });
            if (data.getOrderNumber() != null) {
                holder.oderNumber.setText(data.getOrderNumber());
            }
            if (data.getCustomerName() != null) {
                holder.customerName.setText(data.getCustomerName());
            }
            if (data.getOrderAmount() != null) {
                holder.orderAmount.setText(data.getOrderAmount());
            }
            if (data.getCouponNumber() != null) {
                holder.couponCodr.setText(data.getCouponNumber());
            }
            if (data.getDiscountAmount() != null) {
                holder.discountAmountm.setText(data.getDiscountAmount());
            }

            if (data.getTransactionId() != null) {
                holder.transactionId.setText(data.getTransactionId());
            }
            if (data.getPaymentType() != null) {
                holder.paymentType.setText(data.getPaymentType());
            }
            if (data.getPaymentStatus() != null) {
                holder.paymentStatus.setText(data.getPaymentStatus());
            }

            if (data.getPaymentStatus() != null) {
                holder.paymentStatus.setText(data.getPaymentStatus());
            }

            if (data.getPaymentDate() != null) {
                holder.paymentDate.setText(data.getPaymentDate());
            }
            if (data.getAppointmentId() == null || data.getAppointmentId().equalsIgnoreCase("")) {
                holder.services.setText("No");
            } else {
                holder.services.setText("YES");
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size() + 1;
    }
}

class OrderListHeaderHolder extends RecyclerView.ViewHolder {

    public OrderListHeaderHolder(@NonNull View itemView) {
        super(itemView);
    }
}

class OrderListContentHolder extends RecyclerView.ViewHolder {
    TextView oderNumber, customerName, orderAmount, couponCodr, discountAmountm, transactionId, services, paymentType, paymentStatus, paymentDate, action;

    public OrderListContentHolder(@NonNull View itemView) {
        super(itemView);
        oderNumber = itemView.findViewById(R.id.orderNumberOrderListContent);
        customerName = itemView.findViewById(R.id.customerNameOrderListContent);
        orderAmount = itemView.findViewById(R.id.orderAmountOrderList);
        couponCodr = itemView.findViewById(R.id.couponCodeOrderListContent);
        discountAmountm = itemView.findViewById(R.id.discountAmountOrderListContent);
        transactionId = itemView.findViewById(R.id.transactionIdOrderListContent);
        services = itemView.findViewById(R.id.serviceOrderListContent);
        paymentType = itemView.findViewById(R.id.paymentTypeOrderListContent);
        paymentStatus = itemView.findViewById(R.id.paymentStatusOrderListContent);
        paymentDate = itemView.findViewById(R.id.paymentDateOrderListContent);
        action = itemView.findViewById(R.id.actionOrderListContent);

    }
}