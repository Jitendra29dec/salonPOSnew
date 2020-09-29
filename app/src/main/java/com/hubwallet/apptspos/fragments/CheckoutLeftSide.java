package com.hubwallet.apptspos.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AppointmentsServices;
import com.hubwallet.apptspos.APis.GetCart;
import com.hubwallet.apptspos.APis.GetProductLList;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Adapters.ProductListAdapter;
import com.hubwallet.apptspos.Adapters.ServicesAdapter;
import com.hubwallet.apptspos.Adapters.cartProductAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.CartModel.CartData;
import com.hubwallet.apptspos.Utils.Models.CartModel.CartList;
import com.hubwallet.apptspos.Utils.Models.CheckoutServicesModel.CheckoutServiceData;
import com.hubwallet.apptspos.Utils.Models.CheckoutServicesModel.CheckoutServices;
import com.hubwallet.apptspos.Utils.Models.ProductModel.ProductData;
import com.hubwallet.apptspos.Utils.Models.ProductModel.Products;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CheckoutLeftSide extends Fragment implements ApiCommunicator {
    ApiCommunicator apiCommunicator;
    private RecyclerView horizontzalRecyclerView;
    private RecyclerView itemRecyclerView;
    private Button addItem;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ProgressBar progressBar;
    private cartProductAdapter productAdapter;
    private Communicator communicator;
    private String appointmentID = "";
    private ImageButton nextButton;
    private Button next;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkout_left_fragment, null, false);

        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        if (getArguments() != null) {
            appointmentID = getArguments().getString("appointment_id", "");

        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
        if (!appointmentID.isEmpty()) {
            MySingleton.getInstance(getContext()).addToRequestQue(new AppointmentsServices(appointmentID, new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());
        }
        getCart();
        nextButton = view.findViewById(R.id.imageButtonCheckoutNext);

        horizontzalRecyclerView = view.findViewById(R.id.servicesRecyclerView);
        addItem = view.findViewById(R.id.addItemButton);
        itemRecyclerView = view.findViewById(R.id.itemRecyclerView);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.sendmessage("check_out_swipe_data", "1");
            }
        });
        next = view.findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.sendmessage("check_out_swipe_data", "1");
            }
        });


        addItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MySingleton.getInstance(getContext()).addToRequestQue(new GetProductLList(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
                View vvv = inflater.inflate(R.layout.product_item_view, null, false);
                dialog = new AlertDialog.Builder(getContext()).create();
                recyclerView = vvv.findViewById(R.id.recyclerViewDialogProduct);
                dialog.setView(vvv);
                Button okButton;
                okButton = vvv.findViewById(R.id.okButton);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productListAdapter.getItemsArray();
                        productListAdapter.update(appointmentID, apiCommunicator, true);
                        dialog.dismiss();
                    }
                });


            }
        });
        return view;
    }

    private void getCart() {
        MySingleton.getInstance(getContext()).addToRequestQue(new GetCart(appointmentID, "0", apiCommunicator).getStringRequest());
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("appointment_service")) {

            List<CheckoutServiceData> list = new GsonBuilder().create().fromJson(response, CheckoutServices.class).getResult();
            setAdapter(list);

        }

        if (status.equalsIgnoreCase("products_list")) {
            List<ProductData> list = new GsonBuilder().create().fromJson(response, Products.class).getResult();
            setProductAdapter(list);
        }
        if (status.equalsIgnoreCase("add_cart")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("checkout", appointmentID);

        }
        if (status.equalsIgnoreCase("get_cart")) {
            List<CartData> list = new GsonBuilder().create().fromJson(response, CartList.class).getResult();
            setCartAdapter(list);
        }
        if (status.equalsIgnoreCase("delete_cart")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("checkout", appointmentID);
        }

    }

    private void setCartAdapter(List<CartData> list) {
        productAdapter = new cartProductAdapter(getContext(), list, apiCommunicator);
        itemRecyclerView.setAdapter(productAdapter);

    }

    private void setProductAdapter(List<ProductData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        productListAdapter = new ProductListAdapter(getContext(), list);
        recyclerView.setAdapter(productListAdapter);
        dialog.show();
    }

    private void setAdapter(List<CheckoutServiceData> list) {
        horizontzalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        horizontzalRecyclerView.setAdapter(new ServicesAdapter(getContext(), list));
        progressBar.setVisibility(View.INVISIBLE);

    }
}
