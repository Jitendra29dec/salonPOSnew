package com.hubwallet.apptspos.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddCustomer;
import com.hubwallet.apptspos.APis.AddToCart;
import com.hubwallet.apptspos.APis.GetCart;
import com.hubwallet.apptspos.APis.GetCustomers;
import com.hubwallet.apptspos.APis.GetProductLList;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.ProductListAdapter;
import com.hubwallet.apptspos.Adapters.cartProductAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.CartModel.CartData;
import com.hubwallet.apptspos.Utils.Models.CartModel.CartList;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomer;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomerData;
import com.hubwallet.apptspos.Utils.Models.ProductModel.ProductData;
import com.hubwallet.apptspos.Utils.Models.ProductModel.Products;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SalesLeftSide extends Fragment implements ApiCommunicator {

    ApiCommunicator apiCommunicator;
    private RecyclerView itemRecyclerView;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private Communicator communicator;
    private String appointmentID = "";
    private AutoCompleteTextView customerAutoCom;
    private List<GetCustomerData> customerList;
    private String iid = "";
    private AlertDialog alertDialog;
    private ArrayList<String> cList;
    private ImageButton nextButton;
    private EditText barcodeEditText;
    private List<ProductData> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sales_left_fragment, null, false);

        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        TextView addcustoemer = view.findViewById(R.id.addCustomerTextVeiw);
        addcustoemer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
        customerAutoCom = view.findViewById(R.id.customerAutoComplete);
        Button addItem = view.findViewById(R.id.addItemButton);
        barcodeEditText = view.findViewById(R.id.barcodeEditTextSales);
        barcodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!iid.isEmpty()) {
                    boolean isFound = false;
                    for (ProductData data : list) {
                        if (data.getBarcodeId().equalsIgnoreCase(s.toString()) && s.toString().length() > 10) {
                            isFound = true;
                            MySingleton.getInstance(getContext()).addToRequestQue(new AddToCart(data.getProductId(), "1", "", iid, apiCommunicator).getStringRequest());

                        }

                    }
                    if (!isFound) {

                    }
                } else {
                    barcodeEditText.setText("");
                    Toast.makeText(getContext(), "Select Customer First!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nextButton = view.findViewById(R.id.salesNextImageButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.sendmessage("sales_swipe_data", "1");
            }
        });
        itemRecyclerView = view.findViewById(R.id.itemRecyclerView);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        MySingleton.getInstance(getContext()).addToRequestQue(new GetCustomers(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        MySingleton.getInstance(getContext()).addToRequestQue(new GetProductLList(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());


        customerAutoCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < cList.size(); i++) {
                    if (cList.get(i).equalsIgnoreCase(customerAutoCom.getText().toString())) {
                        iid = customerList.get(i).getCustomerId();
                        barcodeEditText.requestFocus();

                    }
                }
                getCart(iid);

            }

        });


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vvv = inflater.inflate(R.layout.product_item_view, null, false);
                dialog = new AlertDialog.Builder(getContext()).create();
                recyclerView = vvv.findViewById(R.id.recyclerViewDialogProduct);
                dialog.setView(vvv);
                Button okButton;
                okButton = vvv.findViewById(R.id.okButton);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("onClick: ", iid);
                        if (!iid.isEmpty()) {
                            productListAdapter.getItemsArray();
                            productListAdapter.update(iid, apiCommunicator, false);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Select customer first.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                setProductAdapter(list);


            }
        });
        return view;
    }

    private void createDialog() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.add_new_customer, null, false);
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setView(v);
        final EditText firstname, lastName, email, mobile;
        Button cancelButton, addCustomerButton;
        firstname = v.findViewById(R.id.firstNameField);
        lastName = v.findViewById(R.id.lastNameField);
        email = v.findViewById(R.id.emailField);
        mobile = v.findViewById(R.id.mobileField);
        cancelButton = v.findViewById(R.id.cancelButton);
        addCustomerButton = v.findViewById(R.id.addCustomerBUtton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = firstname.getText().toString();
                String lname = lastName.getText().toString();
                String ema = email.getText().toString();
                String mob = mobile.getText().toString();
                if (fname.isEmpty()) {
                    sendError(firstname);
                    return;
                } else if (lname.isEmpty()) {
                    sendError(lastName);
                    return;
                } else if (ema.isEmpty()) {
                    sendError(email);
                    return;
                } else if (mob.isEmpty()) {
                    sendError(mobile);
                    return;
                } else {
                    MySingleton.getInstance(getContext()).addToRequestQue(new AddCustomer(fname, lname, ema, mob, "", "", "", "", "", new PrefManager(getContext()).getVendorId(), "", apiCommunicator).getStringRequest());
                }
            }
        });
        alertDialog.show();

    }

    private void sendError(EditText firstname) {
        firstname.setError("Field Cant be Empty");
    }

    private void getCart(String id) {

        MySingleton.getInstance(getContext()).addToRequestQue(new GetCart("0", id, apiCommunicator).getStringRequest());
    }


    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("products_list")) {
            list = new GsonBuilder().create().fromJson(response, Products.class).getResult();
        }
        if (status.equalsIgnoreCase("add_cart")) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getCart(iid);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            barcodeEditText.setText("");
        }
        if (status.equalsIgnoreCase("get_cart")) {
            Log.e("getApiData: ", response);
            List<CartData> list = new GsonBuilder().create().fromJson(response, CartList.class).getResult();
            setCartAdapter(list);
            communicator.sendmessage("sales", iid);
        }
        if (status.equalsIgnoreCase("delete_cart")) {
            getCart(iid);
        }
        if (status.equalsIgnoreCase("customer_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
            MySingleton.getInstance(getContext()).addToRequestQue(new GetCustomers(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());

        }
        if (status.equalsIgnoreCase("get_customers")) {
            customerList = new GsonBuilder().create().fromJson(response, GetCustomer.class).getResult();
            cList = new ArrayList<>();
            for (GetCustomerData c : customerList) {
                cList.add(c.getCustomerName());
            }
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cList);
            customerAutoCom.setAdapter(customerAdapter);
        }
    }

    private void setCartAdapter(List<CartData> list) {
        cartProductAdapter productAdapter = new cartProductAdapter(getContext(), list, apiCommunicator);
        itemRecyclerView.setAdapter(productAdapter);

    }

    private void setProductAdapter(List<ProductData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        productListAdapter = new ProductListAdapter(getContext(), list);
        recyclerView.setAdapter(productListAdapter);
        dialog.show();
    }

}
