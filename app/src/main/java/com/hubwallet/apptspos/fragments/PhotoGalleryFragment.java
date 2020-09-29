package com.hubwallet.apptspos.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.request.StringRequest;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddCategory;
import com.hubwallet.apptspos.APis.GetBusinessHour;
import com.hubwallet.apptspos.APis.GetPhotoGallery;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.CalenderAdapter;
import com.hubwallet.apptspos.Adapters.HorizontalRecyclerView;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalenderResponce;
import com.hubwallet.apptspos.Utils.Models.Gallery.PhotoData;
import com.hubwallet.apptspos.Utils.Models.Gallery.PhotoDataList;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class PhotoGalleryFragment extends Fragment implements ApiCommunicator {
    private HorizontalRecyclerView adapter;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    private ImageView imgSelectImage;
    private Button upload;
    private String imageString;
    boolean isPicRequired = false;
    private RecyclerView recyclerView;
    byte[] byteArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        recyclerView = view.findViewById(R.id.recyclerViewListItem);
        imgSelectImage = view.findViewById(R.id.imgSelectImage);
        upload = view.findViewById(R.id.addImageButton);
        StringRequest stringRequest = new GetPhotoGallery(new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest();
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
            }
        });

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("photo_list")) {
            List<PhotoData> list = new GsonBuilder().create().fromJson(response, PhotoDataList.class).getResult();
            setAdapter(list);
        }
    }


    private void setAdapter(List<PhotoData> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new HorizontalRecyclerView(getContext(), list, apiCommunicator);
        recyclerView.setAdapter(adapter);
        // recyclerView.setAdapter(new CategoryListAdapter(getContext(), list, apiCommunicator));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if  (requestCode == 101 && resultCode == RESULT_OK && data != null) {
                Uri imageUri = data.getData();
            String path = getPath1(imageUri);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                imgSelectImage.setImageBitmap(bitmap);
                isPicRequired = true;//if get path thn set its true.
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteArray = stream.toByteArray();
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                imageUpload(path);
                // progressBar.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath1(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else
            return uri.getPath();
    }

    private void imageUpload (String filepath){
        ((NavigationActivity) getActivity()).onProgress();
        File file = new File(filepath);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("vendor_id", "1")
                .addFormDataPart("photo", file.getName(), RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://159.203.182.165/salon/api/settings/addPhotoGallery")
                .post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                // imageUploading.dismiss();
                //  String resultResponse = new String(response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        StringRequest stringRequest = new GetPhotoGallery(new PrefManager(getContext()).getVendorId(), apiCommunicator).getStringRequest();
                      //  stringRequest.setShouldCache(false);
                        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
