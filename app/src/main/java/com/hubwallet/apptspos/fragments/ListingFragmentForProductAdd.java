package com.hubwallet.apptspos.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddProduct;
import com.hubwallet.apptspos.APis.EditProduct;
import com.hubwallet.apptspos.APis.GetProductBrand;
import com.hubwallet.apptspos.APis.GetProductById;
import com.hubwallet.apptspos.APis.GetProductCategory;
import com.hubwallet.apptspos.APis.GetVendor;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.BrandModel.BrandData;
import com.hubwallet.apptspos.Utils.Models.BrandModel.GetBrand;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;
import com.hubwallet.apptspos.Utils.Models.GetProductByIdModel.GetProductByIdM;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierList;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategory;
import com.hubwallet.apptspos.Utils.Models.ProductCategoryModel.ProductCategoryData;
import com.hubwallet.apptspos.Utils.Models.TaxList;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class ListingFragmentForProductAdd extends Fragment implements ApiCommunicator {
    //public class ListingFragmentForProductAdd extends DialogFragment implements ApiCommunicator {
    private static final String TAG = ListingFragmentForProductAdd.class.getSimpleName();
    Spinner brandSpinner, categorySpinner, vendorSpinner;
    ApiCommunicator apiCommunicator;
    EditText name, sku, wholesalePrice, retailPrice, purchasePrice, lowQuantitityWarnig, description, barcode,
            availableQuantity, commissionPercent, commissionAmount;
    Button addImageButton;
    AppCompatTextView submitButton;
    CheckBox useForBusinessCheckBok;
    RadioGroup groupTax, groupBusiness;
    RadioButton rTax, rNonTax, rYes, rNo;
    private ImageView imgSelectImage;
    boolean isPicRequired = false;
    private int pick = 101;
    private String imageString = "";
    private ProgressBar progressBar;
    private List<BrandData> brandList;
    private List<SupplierData> vendorList;
    private List<ProductCategoryData> categoryList;
    private Communicator communicator;
    private String productId = "";
    private String isTax = "0";
    private String businessUse = "0";
    private boolean isForEdit = false;
    AppCompatTextView productTitle;
    TextView titleTaxType;
    LinearLayout layoutTaxType;
    private CheckBox chTax1, chTax2, chTax3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString("product_id", "");
            if (!productId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", productId);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_listing_product_add, null, false);
        ButterKnife.bind(this, v);
        productTitle = v.findViewById(R.id.productTitle);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initialize(view);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), pick);
            }
        });
        if (isForEdit) {
            productTitle.setText("Edit Product");
            submitButton.setText("Update");
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  progressBar.setVisibility(View.VISIBLE);
                if (Check(barcode)) {
                    if (Check(name)) {
                        if (Check(sku)) {
                            if (Check(description)) {
                                if (Check(purchasePrice)) {
                                    if (Check(wholesalePrice)) {
                                        if (Check(retailPrice)) {
                                            if (Check(lowQuantitityWarnig)) {
                                                if (Check(availableQuantity)) {
                                                    if (Check(commissionPercent)) {
                                                        if (Check(commissionAmount)) {
                                                            ((NavigationActivity) getActivity()).onProgress();
                                                            String brandId, categoryId, supplierId;
                                                            supplierId = vendorList.get(vendorSpinner.getSelectedItemPosition() != 0 ? vendorSpinner.getSelectedItemPosition() - 1 : vendorSpinner.getSelectedItemPosition()).getSupplierId();
                                                            brandId = brandList.get(brandSpinner.getSelectedItemPosition() != 0 ? brandSpinner.getSelectedItemPosition() - 1 : brandSpinner.getSelectedItemPosition()).getBrandId();
                                                            categoryId = categoryList.get(categorySpinner.getSelectedItemPosition() != 0 ? categorySpinner.getSelectedItemPosition() - 1 : categorySpinner.getSelectedItemPosition()).getCategoryId();
                                                /*if (useForBusinessCheckBok.isChecked()) {
                                                    inBusinessUse = "1";
                                                }*/
                                                            List<TaxList> taxList = new ArrayList<>();

                                                            if (chTax1.isChecked()) {
                                                                TaxList list = new TaxList();
                                                                list.setTax_id("1");
                                                                list.setTax_type("1");
                                                                list.setTax_rate("3%");
                                                                taxList.add(list);
                                                            }
                                                            if (chTax2.isChecked()) {
                                                                TaxList list2 = new TaxList();
                                                                list2.setTax_id("2");
                                                                list2.setTax_type("2");
                                                                list2.setTax_rate("4%");
                                                                taxList.add(list2);
                                                            }
                                                            if (chTax3.isChecked()) {
                                                                TaxList list2 = new TaxList();
                                                                list2.setTax_id("3");
                                                                list2.setTax_type("3");
                                                                list2.setTax_rate("5%");
                                                                taxList.add(list2);
                                                            }
                                                            if (rTax.isChecked()) {
                                                                isTax = "1";
                                                            } else {
                                                                isTax = "0";
                                                            }

                                                            if (rYes.isChecked()) {
                                                                businessUse = "1";
                                                            } else {
                                                                businessUse = "0";
                                                            }

                                                            ArrayList<HashMap<String, String>> taxData = new ArrayList<>();
                                                            for (TaxList tax : taxList) {
                                                                HashMap<String, String> taxDataMap = new HashMap<>();
                                                                taxDataMap.put("tax_id", tax.getTax_id());
                                                                taxDataMap.put("tax_type", tax.getTax_type());
                                                                taxDataMap.put("tax_rate", tax.getTax_rate());
                                                                taxData.add(taxDataMap);
                                                            }
                                                            Gson json = new Gson();
                                                            String tax = json.toJson(taxData);
                                                            Log.d(TAG, "onClick: data of day : " + tax);

                                                            if (isForEdit) {
                                                                MySingleton.getInstance(getContext())
                                                                        .addToRequestQue(new EditProduct(apiCommunicator, new PrefManager(getContext())
                                                                                .getVendorId(),
                                                                                getString(barcode), brandId, categoryId, getString(name),
                                                                                getString(sku), getString(description), getString(retailPrice),
                                                                                getString(wholesalePrice), imageString, businessUse,
                                                                                getString(lowQuantitityWarnig), getString(purchasePrice),
                                                                                productId, getString(availableQuantity), getString(commissionPercent)
                                                                                , getString(commissionAmount), isTax, tax).getStringRequest());
                                                            } else {
                                                                MySingleton.getInstance(getContext())
                                                                        .addToRequestQue(new AddProduct(apiCommunicator, new PrefManager(getContext())
                                                                                .getVendorId(), getString(barcode),
                                                                                supplierId, brandId, categoryId, getString(name),
                                                                                getString(sku), getString(description),
                                                                                getString(retailPrice), getString(wholesalePrice),
                                                                                imageString, getString(lowQuantitityWarnig),
                                                                                getString(purchasePrice), getString(availableQuantity), getString(commissionPercent)
                                                                                , getString(commissionAmount), isTax, businessUse, tax).getStringRequest());
                                                            }
                                                        } else {
                                                            sendError(commissionAmount);
                                                        }

                                                    } else {
                                                        sendError(commissionPercent);
                                                    }

                                                } else {
                                                    sendError(availableQuantity);
                                                }

                                            } else {
                                                sendError(lowQuantitityWarnig);
                                                return;
                                            }
                                        } else {
                                            sendError(retailPrice);
                                            return;
                                        }
                                    } else {
                                        sendError(wholesalePrice);
                                        return;
                                    }
                                } else {
                                    sendError(purchasePrice);
                                    return;
                                }
                            } else {
                                sendError(description);
                                return;
                            }
                        } else {
                            sendError(sku);
                            return;
                        }

                    } else {
                        sendError(name);
                        return;
                    }
                } else {
                    sendError(barcode);
                    return;
                }
            }
        });
    }

    @OnClick(R.id.imgBack)
    public void onCloseClick() {
       /* NavigationActivity.fm.beginTransaction()
                .replace(R.id.containerMain, new ListingFragmentForProductList())
                .commit();*/

        ListingFragmentForProductList listingFragmentForProductList = new ListingFragmentForProductList();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.productMainSubMenu, listingFragmentForProductList, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pick && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                imgSelectImage.setImageBitmap(bitmap);
                isPicRequired = true;//if get path thn set its true.
                addImageButton.setText("Image Selected");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] byteArray = stream.toByteArray();
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                progressBar.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Error Occurred please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendError(EditText name) {
        name.setError("Field can`t be empty.");
    }

    private String getString(EditText name) {

        String val = name.getText().toString();
        return val;
    }

    private boolean Check(EditText name) {
        boolean status = false;
        if (!name.getText().toString().isEmpty() && name.getText().toString().length() > 0) {
            status = true;
        }
        return status;
    }

    private void initialize(View v) {
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        progressBar = v.findViewById(R.id.progressBarAddProduct);
        addImageButton = v.findViewById(R.id.addImageButton);
        name = v.findViewById(R.id.productNAmeEditText);
        submitButton = v.findViewById(R.id.submitButton);
        useForBusinessCheckBok = v.findViewById(R.id.checkboxUseForBusiness);
        sku = v.findViewById(R.id.skuEdiText);
        wholesalePrice = v.findViewById(R.id.wholeSalePriceEdiText);
        retailPrice = v.findViewById(R.id.retailPriceEditText);
        purchasePrice = v.findViewById(R.id.purchasePriceEdiText);
        lowQuantitityWarnig = v.findViewById(R.id.lowQtyEditText);
        description = v.findViewById(R.id.descriptionEditText);
        groupTax = v.findViewById(R.id.rGroupTax);
        rTax = v.findViewById(R.id.rBtnTax);
        rNonTax = v.findViewById(R.id.rBtnNonTax);
        groupBusiness = v.findViewById(R.id.rGroupBusiness);
        rYes = v.findViewById(R.id.rBtnYes);
        rNo = v.findViewById(R.id.rBtnNo);
        imgSelectImage = v.findViewById(R.id.imgSelectImage);
        availableQuantity = v.findViewById(R.id.editAvailableQty);
        commissionPercent = v.findViewById(R.id.editCommission);
        commissionAmount = v.findViewById(R.id.editCommissionInDollar);
        layoutTaxType = v.findViewById(R.id.layoutTaxType);
        titleTaxType = v.findViewById(R.id.titleTaxType);
        barcode = v.findViewById(R.id.barcodeEdiText);
        brandSpinner = v.findViewById(R.id.brandSpinner);
        categorySpinner = v.findViewById(R.id.categorySpinner);
        vendorSpinner = v.findViewById(R.id.vendorSpinner);
        chTax1 = v.findViewById(R.id.chTax1);
        chTax2 = v.findViewById(R.id.chTax2);
        chTax3 = v.findViewById(R.id.chTax3);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetVendor(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        MySingleton.getInstance(getContext()).addToRequestQue(new GetProductBrand(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        MySingleton.getInstance(getContext()).addToRequestQue(new GetProductCategory(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());

        groupTax.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rTax.isChecked()) {
                    titleTaxType.setVisibility(View.VISIBLE);
                    layoutTaxType.setVisibility(View.VISIBLE);
                } else if (rNonTax.isChecked()) {
                    titleTaxType.setVisibility(View.GONE);
                    layoutTaxType.setVisibility(View.GONE);
                }
            }
        });

    }


    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_product_brand")) {
            brandList = new GsonBuilder().create().fromJson(response, GetBrand.class).getMessage();
            ArrayList<String> list = new ArrayList<>();
            list.add("SELECT");
            for (BrandData b : brandList) {
                list.add(b.getBrandName());
            }
            setSpinnerBrand(list, brandSpinner);
        }
        if (status.equalsIgnoreCase("get_supplier")) {
            vendorList = new GsonBuilder().create().fromJson(response, SupplierList.class).getMessage();
            ArrayList<String> list = new ArrayList<>();
            list.add("SELECT");
            for (SupplierData b : vendorList) {
                list.add(b.getSupplierName());
            }
            setSpinnerBrand(list, vendorSpinner);
        }
        if (status.equalsIgnoreCase("get_product_category")) {
            categoryList = new GsonBuilder().create().fromJson(response, ProductCategory.class).getResult();
            ArrayList<String> list = new ArrayList<>();
            list.add("SELECT");
            for (ProductCategoryData b : categoryList) {
                list.add(b.getCategoryName());
            }
            setSpinnerBrand(list, categorySpinner);
            if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
                MySingleton.getInstance(getContext()).addToRequestQue(new GetProductById(productId, new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest());
            }
        }

        if (status.equalsIgnoreCase("product_added")) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("listing", "");
        }
        if (status.equalsIgnoreCase("product_added_failed")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
        }
        if (status.equalsIgnoreCase("product_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("listing", "");

        }
        if (status.equalsIgnoreCase("get_product_by_id")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetProductByIdM product = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetProductByIdM.class);
                updatate(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatate(GetProductByIdM product) {
        name.setText(product.getProductName());
        barcode.setText(product.getBarcodeId());
        sku.setText(product.getSku());
        description.setText(product.getDescription());
        purchasePrice.setText(product.getPurchasePrice());
        retailPrice.setText(product.getPriceRetail());
        wholesalePrice.setText(product.getPriceWholesale());
        lowQuantitityWarnig.setText(product.getLowQtyWarning());
        availableQuantity.setText(product.getQuantity());
        commissionPercent.setText(product.getCommission_percent());
        commissionAmount.setText(product.getCommission_amount());
        for (int i = 0; i < brandList.size(); i++) {
            if (brandList.get(i).getBrandId().equalsIgnoreCase(product.getBrandId())) {
                brandSpinner.setSelection(i + 1);
            }
        }
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryId().equalsIgnoreCase(product.getCategoryId())) {
                categorySpinner.setSelection(i + 1);
            }
        }
        for (int i = 0; i < vendorList.size(); i++) {
            if (vendorList.get(i).getSupplierId().equalsIgnoreCase(product.getSupplier_id())) {
                vendorSpinner.setSelection(i + 1);
            }
        }
        if (product.getBusinessUse().equalsIgnoreCase("1")) {
            rYes.setChecked(true);
        } else {
            rNo.setChecked(true);
        }

        if (product.getIs_tax().equalsIgnoreCase("1")) {
            rTax.setChecked(true);
        } else {
            rNonTax.setChecked(true);
        }
    }

    private void setSpinnerBrand(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }

}
