package com.hubwallet.apptspos.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetCheckOutList;
import com.hubwallet.apptspos.APis.GetDiscountApi;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Adapters.CheckoutDetailsAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.CheckoutDetails.CheckoutData;
import com.hubwallet.apptspos.Utils.Models.DiscountModel.Discount;
import com.hubwallet.apptspos.Utils.Models.DiscountModel.ProductDiscount;
import com.hubwallet.apptspos.Utils.Models.DiscountModel.ServiceDiscount;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CheckoutRightSide extends Fragment implements ApiCommunicator {
    RecyclerView recyclerView;
    ApiCommunicator apiCommunicator;
    private TextView serviceTotal, cartTotal, totalPrice, topPrice;
    private String appointmentId = "";
    private float disccountedPrice, TotalPrice;
    private TextView cartLeft, serviceLeft;
    private String paymentType = "0";
    private CheckoutDetailsAdapter checkoutDetailsAdapter;
    private String customerID = "";
    private ImageButton previousButton;
    private Communicator communicator;
    private String fragmentType = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.sales_fragment2, null, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        if (getArguments() != null) {
            fragmentType = getArguments().getString("fragmentType", "");
            appointmentId = getArguments().getString("appointment_id", "");
        }
        final TextView pay = view.findViewById(R.id.paymentTextVIEwFragment);
        serviceTotal = view.findViewById(R.id.serviceTotal);
        cartTotal = view.findViewById(R.id.cartTotal);
        totalPrice = view.findViewById(R.id.totalTexView);
        topPrice = view.findViewById(R.id.topPriceView);
        recyclerView = view.findViewById(R.id.recyclerViewBillDetails);
        cartLeft = view.findViewById(R.id.cartTextViewLeft);
        serviceLeft = view.findViewById(R.id.serviceTextViewLeft);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        previousButton = view.findViewById(R.id.previousImageCheckoutRight);
        if (!appointmentId.isEmpty()) {
            update();
        }
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentType.equalsIgnoreCase("")) {
                    communicator.sendmessage("check_out_swipe_data", "0");
                } else {
                    communicator.sendmessage("sales_swipe_data", "0");
                }
            }
        });
        TextView certificateTextView, giftCardTextView;

        giftCardTextView = view.findViewById(R.id.giftCardTextView);
        giftCardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.customDialog2).create();
                View view1 = getLayoutInflater().inflate(R.layout.alert_dailog_layout_gift, null, false);
                dialog.setView(view1);
                dialog.show();
            }
        });
        certificateTextView = view.findViewById(R.id.certificateTextView);
        certificateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.customDialog2).create();
                View view1 = getLayoutInflater().inflate(R.layout.alert_dailog_layout_certificate, null, false);
                dialog.setView(view1);
                dialog.show();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.sendmessage("payment_frag", "");
         /*       final AlertDialog paymentOptionDialog = new AlertDialog.Builder(getContext()).create();
                View view = getLayoutInflater().inflate(R.layout.fragment_payment_option, null, false);
                paymentOptionDialog.setView(view);
                TextView payment=view.findViewById(R.id.paymentAlertTotal);
                payment.setText(topPrice.getText().toString());
                RelativeLayout byCash = view.findViewById(R.id.byCashRelativeLayout);
                byCash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      paymentOptionDialog.dismiss();
                        paymentType = "1";// by default, need to be changed
                        if (customerID.equalsIgnoreCase("")) {
                            Log.e("onClick: ", "1");
                            MySingleton.getInstance(getContext()).addToRequestQue(new AddOrder("0", new PrefManager(getContext()).getVendorId(), appointmentId, paymentType, apiCommunicator).getStringRequest());
                        } else if (appointmentId.equalsIgnoreCase("")) {
                            Log.e("onClick: ", "2");
                            MySingleton.getInstance(getContext()).addToRequestQue(new AddOrder(customerID, new PrefManager(getContext()).getVendorId(), "0", paymentType, apiCommunicator).getStringRequest());
                        }

                    }
                });
                paymentOptionDialog.show();*/
            }
        });

        return view;
    }

    private void update() {
        MySingleton.getInstance(getContext()).addToRequestQue(new GetCheckOutList(appointmentId, "0", new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());

    }

    public void updateForSales(String id) {
        MySingleton.getInstance(getContext()).addToRequestQue(new GetCheckOutList("0", id, new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());
        customerID = id;
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("get_checkout_list")) {
            CheckoutData data = new GsonBuilder().create().fromJson(response, CheckoutData.class);
            setAdapter(data);
            if (data.getServiceTotal() != null) {
                serviceTotal.setText("$" + data.getServiceTotal() + "");

            } else {
                serviceLeft.setText("");
                serviceTotal.setText("");
            }
            if (data.getCartTotal() != null) {
                cartTotal.setText("$" + data.getCartTotal() + "");
            } else {
                cartLeft.setText("");
                cartTotal.setText("");
            }
            totalPrice.setText("$" + data.getTotalPrice());
            topPrice.setText("$" + data.getTotalPrice());
            TotalPrice = Float.parseFloat(data.getTotalPrice());
        }
        if (status.equalsIgnoreCase("add_order")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                Communicator c = (Communicator) getActivity();
                c.sendmessage("order_detail", jsonObject.getString("order_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (status.equalsIgnoreCase("discount_response")) {
            Log.e("DicscountData: ", response);
            List<ProductDiscount> productDiscounts = new GsonBuilder().create().fromJson(response, Discount.class).getProductDiscount();
            List<ServiceDiscount> serviceDiscounts = new GsonBuilder().create().fromJson(response, Discount.class).getServiceDiscount();
            List<String> discountServiceData = new ArrayList<>();
            discountServiceData.add("Select Discount");
            for (ServiceDiscount discount : serviceDiscounts) {
                if (discount.getDiscountType().equalsIgnoreCase("Flat")) {
                    discountServiceData.add("$" + discount.getDiscount());
                } else {
                    discountServiceData.add(discount.getDiscount() + "%");

                }
            }
            List<String> discountProductData = new ArrayList<>();
            discountProductData.add(("Select Discount"));
            for (ProductDiscount discount : productDiscounts) {
                if (discount.getDiscountType().equalsIgnoreCase("Flat")) {
                    discountProductData.add("$" + discount.getDiscount());
                } else {
                    discountProductData.add(discount.getDiscount() + "%");

                }
            }
            setServiceDiscountAdapter(discountServiceData);
            setServiceProductAdapter(discountProductData);
        }
    }

    private void setServiceProductAdapter(List<String> discountProductData) {
        checkoutDetailsAdapter.updateProductDiscount(discountProductData);
    }

    private void setServiceDiscountAdapter(List<String> discountServiceData) {
        checkoutDetailsAdapter.updateServiceDiscount(discountServiceData);
    }

    private void setAdapter(CheckoutData data) {
        checkoutDetailsAdapter = new CheckoutDetailsAdapter(getContext(), data);
        recyclerView.setAdapter(checkoutDetailsAdapter);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetDiscountApi(apiCommunicator, new PrefManager(getContext()).getVendorId()).getRequest());

    }
}

