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
import com.hubwallet.apptspos.APis.AddCategory;
import com.hubwallet.apptspos.APis.EditCategory;
import com.hubwallet.apptspos.APis.GetCategoryById;

import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetCategoryByData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class AddCategoryFragment extends Fragment implements ApiCommunicator, View.OnClickListener {
    private AppCompatTextView submitBtn, backBtn;
    private EditText categoryName, description;
    private Communicator communicator;
    ApiCommunicator apiCommunicator;
    private boolean isForEdit = false;
    private String categoryId = "";
    AppCompatTextView categoryTitle;
    private ImageView imgSelectImage;
    boolean isPicRequired = false;
    Button addImageButton;
    private String imageString = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString("category_id", "");
            if (!categoryId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", categoryId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, null, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        imgSelectImage = view.findViewById(R.id.imgSelectImage);
        addImageButton = view.findViewById(R.id.addImageButton);
        categoryTitle = view.findViewById(R.id.categoryTitle);
        categoryName = view.findViewById(R.id.categoryEdiText);
        description = view.findViewById(R.id.descriptionEditText);
        submitBtn = view.findViewById(R.id.submitButton);
        backBtn = view.findViewById(R.id.imgBack);
        submitBtn.setOnClickListener(this);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
            }
        });
        if (isForEdit) {
            categoryTitle.setText("Edit Category");
            submitBtn.setText("Update");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.productMainSubMenu, new CategoryListFragment())
                        .commit();
            }
        });

        submitBtn.setOnClickListener(view1 -> {
            if (!categoryName.getText().toString().isEmpty() && categoryName.getText().toString().length() > 0) {
                ((NavigationActivity) getActivity()).onProgress();
                if (isForEdit) {
                    MySingleton.getInstance(getContext())
                            .addToRequestQue(new EditCategory(new PrefManager(getContext())
                                    .getVendorId(), categoryId, categoryName.getText().toString(),
                                    description.getText().toString(),imageString, apiCommunicator).getStringRequest());
                } else {
                    MySingleton.getInstance(getContext())
                            .addToRequestQue(new AddCategory(new PrefManager(getContext())
                                    .getVendorId(), categoryName.getText().toString(),
                                    description.getText().toString(),imageString, apiCommunicator).getStringRequest());
                }

            } else {
                categoryName.setError("Enter category name");
            }

        });
        if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
            MySingleton.getInstance(getContext()).addToRequestQue(new GetCategoryById(apiCommunicator, new PrefManager(getContext()).getVendorId(), categoryId).getStringRequest());
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
       /* if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
            MySingleton.getInstance(getContext()).addToRequestQue(new GetCategoryById(apiCommunicator, categoryId, new PrefManager(getContext()).getVendorId()).getStringRequest());
        }*/

        if (status.equalsIgnoreCase("get_category_byId")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetCategoryByData category = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetCategoryByData.class);
                updatate(category);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (status.equalsIgnoreCase("category_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("category_added", "");
        }

        if (status.equalsIgnoreCase("category_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("category_added", "");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitButton:
                break;
        }
    }

    private void addCategory() {

    }

    private void updatate(GetCategoryByData category) {
        categoryName.setText(category.getCategoryName());
        description.setText(category.getDescription());
    }
}
