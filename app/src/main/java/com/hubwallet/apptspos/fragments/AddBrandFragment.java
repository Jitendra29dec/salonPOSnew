package com.hubwallet.apptspos.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddBrand;
import com.hubwallet.apptspos.APis.EditBrand;
import com.hubwallet.apptspos.APis.GetBrandById;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetBrandByData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddBrandFragment extends Fragment implements ApiCommunicator, View.OnClickListener {
    private AppCompatTextView submitBtn, backBtn;
    private EditText brandName;
    private Communicator communicator;
    ApiCommunicator apiCommunicator;
    private boolean isForEdit = false;
    private String brandId = "";
    AppCompatTextView brandTitle;
    private ImageView imgSelectImage;
    boolean isPicRequired = false;
    Button addImageButton;
    private String imageString = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            brandId = getArguments().getString("brand_id", "");
            if (!brandId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", brandId);
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_brand, null , false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        imgSelectImage = view.findViewById(R.id.imgSelectImage);
        addImageButton = view.findViewById(R.id.addImageButton);
        brandTitle = view.findViewById(R.id.brandTitle);
        brandName = view.findViewById(R.id.brandEdiText);
        submitBtn = view.findViewById(R.id.submitButton);
        backBtn = view.findViewById(R.id.imgBack);

        if (isForEdit){
            brandTitle.setText("Edit Brand");
            submitBtn.setText("Update");
        }

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.productMainSubMenu, new BrandListFragment())
                        .commit();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!brandName.getText().toString().isEmpty() && brandName.getText().toString().length() > 0) {
                    ((NavigationActivity) getActivity()).onProgress();
                    if (isForEdit) {
                        MySingleton.getInstance(getContext())
                                .addToRequestQue(new EditBrand(new PrefManager(getContext())
                                        .getVendorId(),brandId,brandName.getText().toString(),apiCommunicator).getStringRequest());
                    } else {
                        MySingleton.getInstance(getContext())
                                .addToRequestQue(new AddBrand(new PrefManager(getContext())
                                        .getVendorId(),brandName.getText().toString(), apiCommunicator).getStringRequest());
                    }
                }else {
                    brandName.setError("Enter Brand name");
                }
            }
        });
        if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
            MySingleton.getInstance(getContext()).addToRequestQue(new GetBrandById(apiCommunicator, new PrefManager(getContext()).getVendorId(),brandId).getStringRequest());
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            // path = getPath1(imageUri);
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
                // progressBar.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("brand_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("brand_added", "");
        }

        if (status.equalsIgnoreCase("brand_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("brand_added", "");
        }
        if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
            MySingleton.getInstance(getContext()).addToRequestQue(new GetBrandById(apiCommunicator,brandId, new PrefManager(getContext()).getVendorId()).getStringRequest());
        }

        if (status.equalsIgnoreCase("get_brand_byId")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetBrandByData brand = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetBrandByData.class);
                updatate(brand);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitButton:
             //   addBrand();
                break;
        }
    }

    private void addBrand(){

    }

    private void updatate(GetBrandByData brand) {
        brandName.setText(brand.getBrandName());
    }
}
