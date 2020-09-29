package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.request.StringRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetProducts;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.PagerAdapterProductSubMenu;
import com.hubwallet.apptspos.Adapters.ProductsAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductData;
import com.hubwallet.apptspos.Utils.Models.GetProductModel.GetProductList;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListingFragmentForProductList extends Fragment implements ApiCommunicator, View.OnClickListener {

    @BindView(R.id.searchProductEditText)
    EditText searchProductEditText;
    @BindView(R.id.recyclerViewListItem)
    RecyclerView recyclerView;
    @BindView(R.id.btnAddProduct)
    Button btnAddProduct;
    @BindView(R.id.tvProduct)
    TextView product;
    @BindView(R.id.tabProduct)
    LinearLayout tabProduct;
    @BindView(R.id.tabCategory)
    LinearLayout tabCategory;
    @BindView(R.id.tabBrand)
    LinearLayout tabBrand;
    @BindView(R.id.tabVendor)
    LinearLayout tabVendor;
    @BindView(R.id.tabPurchaseOrder)
    LinearLayout tabPurchaseOrder;
    @BindView(R.id.tabReceivedPurchase)
    LinearLayout tabReceivedPurchase;
    @BindView(R.id.tabReturnPurchase)
    LinearLayout tabReturnPurchase;
    private ProductsAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapterProductSubMenu viewPagerAdapter;
    ApiCommunicator apiCommunicator;
    Communicator communicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_listing_product_list, null, false);
        ButterKnife.bind(this, v);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        ((NavigationActivity) getActivity()).onProgress();
        StringRequest stringRequest = new GetProducts(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
        tabProduct.setOnClickListener(this);
        tabCategory.setOnClickListener(this);
        tabBrand.setOnClickListener(this);
        tabVendor.setOnClickListener(this);
        tabPurchaseOrder.setOnClickListener(this);
        tabReceivedPurchase.setOnClickListener(this);
        tabReturnPurchase.setOnClickListener(this);

        searchProductEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }


    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_products")) {
            List<GetProductData> list = new GsonBuilder().create().fromJson(response, GetProductList.class).getResult();
            setAdapter(list);
        }
        if (status.equalsIgnoreCase("delete_product")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("listing", "delete");
        }
        if (status.equalsIgnoreCase("product_id")) {
            communicator.sendmessage("listing", response);
        }
    }

    @OnClick(R.id.btnAddProduct)
    public void onAddProductClick() {
      /*  NavigationActivity.fm.beginTransaction()
                .replace(R.id.containerMain, new ListingFragmentForProductAdd())
                .commit();*/
        ListingFragmentForProductAdd listingFragmentForProductAdd = new ListingFragmentForProductAdd();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.productMainSubMenu, listingFragmentForProductAdd, "findThisFragment")
                .addToBackStack(null)
                .commit();
//        DialogFragment df = new ListingFragmentForProductAdd();
//        df.show(getChildFragmentManager(), "Add Product Fragment");
    }

    private void setAdapter(List<GetProductData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
      //  recyclerView.setAdapter(new ProductsAdapter(getContext(), list, apiCommunicator));
        adapter = new ProductsAdapter(getContext(), list, apiCommunicator);
        recyclerView.setAdapter(adapter);
    }

    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        //  popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.product_popupmenu);
        popupMenu.show();
    }

   /* @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.productList:
                Toast.makeText(getContext(), "ItemClicked1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.category:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new CategoryListFragment())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked2", Toast.LENGTH_LONG).show();
                return true;
            case R.id.brand:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new BrandListFragment())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked3", Toast.LENGTH_LONG).show();
                return true;
            case R.id.vendorList:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new VendorListFragment())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked4", Toast.LENGTH_LONG).show();
                return true;
            case R.id.purchaseOrder:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new PurchaseOrderList())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked5", Toast.LENGTH_LONG).show();
                return true;
            case R.id.receivedPurOrder:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new ReceivePurchaseOrderList())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked6", Toast.LENGTH_LONG).show();
                return true;
            case R.id.returnPurOrder:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new ReturnPurchaseOrderList())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked7", Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabProduct:
                Toast.makeText(getContext(), "ItemClicked1", Toast.LENGTH_LONG).show();
                break;
            case R.id.tabCategory:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new CategoryListFragment())
                        .commit();
              //  Toast.makeText(getContext(), "ItemClicked2", Toast.LENGTH_LONG).show();
                break;
            case R.id.tabBrand:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new BrandListFragment())
                        .commit();
             //   Toast.makeText(getContext(), "ItemClicked3", Toast.LENGTH_LONG).show();
                break;
            case R.id.tabVendor:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new VendorListFragment())
                        .commit();
             //   Toast.makeText(getContext(), "ItemClicked4", Toast.LENGTH_LONG).show();
                break;
            case R.id.tabPurchaseOrder:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new PurchaseOrderList())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked5", Toast.LENGTH_LONG).show();
                break;
            case R.id.tabReceivedPurchase:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new ReceivePurchaseOrderList())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked6", Toast.LENGTH_LONG).show();
                break;
            case R.id.tabReturnPurchase:
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, new ReturnPurchaseOrderList())
                        .commit();
                Toast.makeText(getContext(), "ItemClicked7", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
