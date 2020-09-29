package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Communicator;


public class ProductMenuFragment extends Fragment implements View.OnClickListener, Communicator {
    LinearLayout tabProduct,tabCategory,tabBrand,tabVendor,tabPurchaseOrder,tabReceivePurchaseOrder,tabReturnPurchaseOrder;
    TextView tvProduct,tvCategory,tvBrand,tvVendor,tvPurchaseOrder,tvReceivePurchaseOrder,tvReturnPurchaseOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_menu, container, false);
        tabProduct = view.findViewById(R.id.tabProduct);
        tabCategory = view.findViewById(R.id.tabCategory);
        tabBrand = view.findViewById(R.id.tabBrand);
        tabVendor = view.findViewById(R.id.tabVendor);
        tabPurchaseOrder = view.findViewById(R.id.tabPurchaseOrder);
        tabReceivePurchaseOrder = view.findViewById(R.id.tabReceivedPurchase);
        tabReturnPurchaseOrder = view.findViewById(R.id.tabReturnPurchase);
        tvProduct = view.findViewById(R.id.product);
        tvCategory = view.findViewById(R.id.category);
        tvBrand = view.findViewById(R.id.brand);
        tvVendor = view.findViewById(R.id.vendorList);
        tvPurchaseOrder = view.findViewById(R.id.purchaseOrder);
        tvReceivePurchaseOrder = view.findViewById(R.id.receivedPurchase);
        tvReturnPurchaseOrder = view.findViewById(R.id.returnPurchaseOrder);
        tvProduct.setTextColor(getResources().getColor(R.color.colorPrimary));
        ListingFragmentForProductList listingFragmentForProductList = new ListingFragmentForProductList();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.productMainSubMenu, listingFragmentForProductList, "findThisFragment")
                .addToBackStack(null)
                .commit();

        tabProduct.setOnClickListener(this);
        tabCategory.setOnClickListener(this);
        tabBrand.setOnClickListener(this);
        tabVendor.setOnClickListener(this);
        tabPurchaseOrder.setOnClickListener(this);
        tabReceivePurchaseOrder.setOnClickListener(this);
        tabReturnPurchaseOrder.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tabProduct:
                ((NavigationActivity) getActivity()).onProgress();
                tvProduct.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvCategory.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBrand.setTextColor(getResources().getColor(R.color.setting_colour));
                tvVendor.setTextColor(getResources().getColor(R.color.setting_colour));
                ListingFragmentForProductList listingFragmentForProductList = new ListingFragmentForProductList();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.productMainSubMenu, listingFragmentForProductList, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabCategory:
                ((NavigationActivity) getActivity()).onProgress();
                tvProduct.setTextColor(getResources().getColor(R.color.setting_colour));
                tvCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvBrand.setTextColor(getResources().getColor(R.color.setting_colour));
                tvVendor.setTextColor(getResources().getColor(R.color.setting_colour));
                CategoryListFragment categoryListFragment = new CategoryListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.productMainSubMenu, categoryListFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabBrand:
                ((NavigationActivity) getActivity()).onProgress();
                tvProduct.setTextColor(getResources().getColor(R.color.setting_colour));
                tvCategory.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBrand.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvVendor.setTextColor(getResources().getColor(R.color.setting_colour));
                BrandListFragment brandListFragment = new BrandListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.productMainSubMenu, brandListFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabVendor:
                ((NavigationActivity) getActivity()).onProgress();
                tvProduct.setTextColor(getResources().getColor(R.color.setting_colour));
                tvCategory.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBrand.setTextColor(getResources().getColor(R.color.setting_colour));
                tvVendor.setTextColor(getResources().getColor(R.color.colorPrimary));
                VendorListFragment vendorListFragment = new VendorListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.productMainSubMenu, vendorListFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabPurchaseOrder:
                ((NavigationActivity) getActivity()).onProgress();
                tvProduct.setTextColor(getResources().getColor(R.color.setting_colour));
                tvCategory.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBrand.setTextColor(getResources().getColor(R.color.setting_colour));
                tvVendor.setTextColor(getResources().getColor(R.color.colorPrimary));
                PurchaseOrderList purchaseOrderList = new PurchaseOrderList();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.productMainSubMenu, purchaseOrderList, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabReceivedPurchase:
                ((NavigationActivity) getActivity()).onProgress();
                tvProduct.setTextColor(getResources().getColor(R.color.setting_colour));
                tvCategory.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBrand.setTextColor(getResources().getColor(R.color.setting_colour));
                tvVendor.setTextColor(getResources().getColor(R.color.colorPrimary));
                ReceivePurchaseOrderList receivePurchaseOrderList = new ReceivePurchaseOrderList();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.productMainSubMenu, receivePurchaseOrderList, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tabReturnPurchase:
                break;
        }
    }


    @Override
    public void sendmessage(String message, String response) {
        /*if (message.equalsIgnoreCase("listing")) {
            if (response.equalsIgnoreCase("")) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.productMainSubMenu, new ListingFragmentForProductList()).commit();
            } else if (response.equalsIgnoreCase("delete")) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.productMainSubMenu, new ListingFragmentForProductList()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("product_id", response);
                ListingFragmentForProductAdd fragmentForProductAdd = new ListingFragmentForProductAdd();
                fragmentForProductAdd.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.productMainSubMenu, fragmentForProductAdd).commit();

            }
        }*/
    }
}
