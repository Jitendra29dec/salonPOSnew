package com.hubwallet.apptspos.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.request.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetIouAmount;
import com.hubwallet.apptspos.APis.GetPhotoGallery;
import com.hubwallet.apptspos.APis.GetTvAppSetting;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.AppSettingData;
import com.hubwallet.apptspos.Utils.Models.IouAmountData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class TvAppSetting extends Fragment implements ApiCommunicator {
    private EditText timeInterval, videoLink;
    private ImageView imgSelectImage;
    boolean isPicRequired = false;
    private AppCompatTextView submitButton;
    private String imageString = "";
    Button addImageButton;
    private Spinner statusSpinner;
    ApiCommunicator apiCommunicator;
    byte[] byteArray;
    String path;
    private ArrayList<String> statusList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_app_setting, container, false);
        apiCommunicator = this;
        videoLink = view.findViewById(R.id.videoLink);
        imgSelectImage = view.findViewById(R.id.imgSelectImage);
        timeInterval = view.findViewById(R.id.timeInterval);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        addImageButton = view.findViewById(R.id.addImageButton);
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTvScreen(path);
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQue(new GetTvAppSetting(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
            }
        });

        statusList.add("SELECT");
        statusList.add("Active");
        statusList.add("DeActive");
        setSpinner(statusList, statusSpinner);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            path = getPath1(imageUri);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                imgSelectImage.setImageBitmap(bitmap);
                isPicRequired = true;//if get path thn set its true.
                addImageButton.setText("Image Selected");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                 byteArray = stream.toByteArray();
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                // progressBar.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setSpinner(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
        if (status.equalsIgnoreCase("get_tvSetting")) {
          //  Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject(response);
                AppSettingData appSettingData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), AppSettingData.class);
                getDataFromApi(appSettingData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDataFromApi(AppSettingData appSettingData) {
        videoLink.setText(appSettingData.getVideoUrl());
        timeInterval.setText(appSettingData.getVideoTimeInterval());
        Glide.with(getContext())
                .load(appSettingData.getWallpaper())
                .placeholder(R.drawable.place_holder_1)
                .into(imgSelectImage);
    }

    private void updateTvScreen (String filepath){
        ((NavigationActivity) getActivity()).onProgress();
        File file = new File(filepath);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("vendor_id", "1")
                .addFormDataPart("video_url",videoLink.getText().toString())
                .addFormDataPart("video_time_interval",timeInterval.getText().toString())
                .addFormDataPart("wallpaper",file.getName(), RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://159.203.182.165/salon/api/settings/updateTVscreen")
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
                        StringRequest stringRequest = new GetTvAppSetting(apiCommunicator,new PrefManager(getContext()).getVendorId()).getStringRequest();
                        stringRequest.setShouldCache(false);
                        MySingleton.getInstance(getContext()).addToRequestQue(stringRequest);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
}
