package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.APis.OrderDetails;
import com.hubwallet.apptspos.Adapters.ProductDetailAdapter;
import com.hubwallet.apptspos.Adapters.ServiceDetailAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.OrderDetailModel.CustomerInfo;
import com.hubwallet.apptspos.Utils.Models.OrderDetailModel.OrderDetail;
import com.hubwallet.apptspos.Utils.Models.OrderDetailModel.OrderDetailData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailFragment extends Fragment implements ApiCommunicator {
    private RecyclerView appointmentRecyclerView, productrecyclerVeiw;
    private String orderId = "";
    private TextView customerName, customerEmail, customerMobile, orderNumberTextView, apponintmentHeader;
    private LinearLayout ProductMain;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_details_order, null, false);
        if (getArguments() != null) {
            orderId = getArguments().getString("order_id");
        }
        initialize(v);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productrecyclerVeiw.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void initialize(View v) {
        orderNumberTextView = v.findViewById(R.id.orderNumberTextView);
        appointmentRecyclerView = v.findViewById(R.id.serviceDetailRecyclerView);
        productrecyclerVeiw = v.findViewById(R.id.productDetailRecyclerView);
        customerName = v.findViewById(R.id.customerNameField);
        customerMobile = v.findViewById(R.id.customerMobileNumberFiled);
        customerEmail = v.findViewById(R.id.customerEmailField);
        apponintmentHeader = v.findViewById(R.id.appointmentInfoHeader);
        ProductMain = v.findViewById(R.id.productMain);
    }

    @Override
    public void onResume() {
        super.onResume();
        MySingleton.getInstance(getContext()).addToRequestQue(new OrderDetails(orderId, this).getStringRequest());
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("order_detail")) {
            OrderDetailData data = new GsonBuilder().create().fromJson(response, OrderDetail.class).getResult();
            updateCustomerDetail(data.getCustomerInfo());
            orderNumberTextView.setText(data.getEditData().getOrderNumber());
            if (data.getProductInfo().size() == 0) {
                ProductMain.setVisibility(View.GONE);
            }
            if (data.getAppointmentData().size() == 0) {
                apponintmentHeader.setVisibility(View.GONE);
                appointmentRecyclerView.setVisibility(View.GONE);
            }
            productrecyclerVeiw.setAdapter(new ProductDetailAdapter(getContext(), data.getProductInfo()));
            appointmentRecyclerView.setAdapter(new ServiceDetailAdapter(getContext(), data.getAppointmentData()));
        }
    }

    private void updateCustomerDetail(CustomerInfo customerInfo) {
        customerMobile.setText(customerInfo.getMobilePhone());
        customerEmail.setText(customerInfo.getEmail());
        customerName.setText(customerInfo.getFirstname() + " " + customerInfo.getLastname());

    }
}
