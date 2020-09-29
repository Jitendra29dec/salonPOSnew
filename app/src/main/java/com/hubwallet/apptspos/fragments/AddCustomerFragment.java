package com.hubwallet.apptspos.fragments;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddCustomer;
import com.hubwallet.apptspos.APis.EditCustomer;
import com.hubwallet.apptspos.APis.GetCustomerById;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.GetCustomerById.GetCustomerBy;
import com.hubwallet.apptspos.Utils.Models.GetCustomerById.GetCustomerByIdData;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class AddCustomerFragment extends DialogFragment implements ApiCommunicator {
    EditText firstName, lastName, mobile, address, pincode, note, email, city;
    TextView birthday;
    Button cancelButton, addCustomerButton;
    ApiCommunicator apiCommunicator;
    Communicator communicator;
    boolean isForEdit = false;
    String data;
    Button addImage;
    private boolean isPicRequired = false;
    private String imageString = "";
    private int pick = 102;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.add_customer_fragment, null, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        mobile = view.findViewById(R.id.mobileField);
        address = view.findViewById(R.id.address);
        pincode = view.findViewById(R.id.pincode);
        note = view.findViewById(R.id.NotesField);
        email = view.findViewById(R.id.emailField);
        city = view.findViewById(R.id.city);
        birthday = view.findViewById(R.id.birthdayField);
        addImage = view.findViewById(R.id.addImageCustomer);
        progressBar = view.findViewById(R.id.progressBarAddCustomer);
        addCustomerButton = view.findViewById(R.id.addCustomerButton);

        if (getArguments() != null) {
            data = getArguments().getString("info", "0");
            if (!data.equalsIgnoreCase("0")) {
                isForEdit = true;
                progressBar.setVisibility(View.VISIBLE);

                MySingleton.getInstance(getContext())
                        .addToRequestQue(new GetCustomerById(data, apiCommunicator)
                                .getStringRequest());

            }
        }

        addImage.setOnClickListener(v1 -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), pick);
        });

        birthday.setOnClickListener(v12 -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    birthday.setText(year + "/" + month + "/" + dayOfMonth);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        addCustomerButton.setOnClickListener(v13 -> {
            if (firstName.getText().toString().isEmpty()) {
                sendError(firstName);
                return;
            }
            if (lastName.getText().toString().isEmpty()) {
                sendError(lastName);
                return;
            }
            if (mobile.getText().toString().isEmpty()) {
                sendError(mobile);
                return;
            }
            if (address.getText().toString().isEmpty()) {
                sendError(address);
                return;
            }
            if (pincode.getText().toString().isEmpty()) {
                sendError(pincode);
                return;
            }
            if (email.getText().toString().isEmpty()) {
                sendError(email);
                return;
            }
            if (city.getText().toString().isEmpty()) {
                sendError(city);
                return;
            }
            if (birthday.getText().toString().isEmpty()) {
                birthday.setError("Field cant be empty");
                return;
            }
            if (!isForEdit) {
                MySingleton.getInstance(getContext()).addToRequestQue(new AddCustomer(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), mobile.getText().toString(), address.getText().toString(), pincode.getText().toString(), city.getText().toString(), note.getText().toString(), birthday.getText().toString(), new PrefManager(getContext()).getVendorId(), imageString, apiCommunicator).getStringRequest());
                progressBar.setVisibility(View.VISIBLE);

            } else {
                MySingleton.getInstance(getContext()).addToRequestQue(new EditCustomer(data, firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), mobile.getText().toString(), address.getText().toString(), pincode.getText().toString(), city.getText().toString(), note.getText().toString(), birthday.getText().toString(), new PrefManager(getContext()).getVendorId(), imageString, apiCommunicator).getStringRequest());
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    @OnClick({R.id.btnCancel, R.id.activityCloseButton})
    public void onCloseClick() {
        this.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pick && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                isPicRequired = true;//if get path thn set its true.
                addImage.setText("Image Selected");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] byteArray = stream.toByteArray();
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "Error Occurred please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendError(EditText firstName) {
        firstName.setError("Field cant be empty");
    }

    @Override
    public void getApiData(String status, String response) {
        Log.e("getApiData: ", response);
        if (status.equalsIgnoreCase("customer_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("customers", "");
            progressBar.setVisibility(View.GONE);
        }
        if (status.equalsIgnoreCase("customer_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("customers", "");
            progressBar.setVisibility(View.GONE);
        }
        if (status.equalsIgnoreCase("get_customer_by_id")) {
            List<GetCustomerByIdData> list = new GsonBuilder().create().fromJson(response, GetCustomerBy.class).getResult();
            updateData(list.get(0));
        }
    }

    private void updateData(GetCustomerByIdData getCustomerByIdData) {

        if (getCustomerByIdData.getCustomerName() != null) {
            String[] names = getCustomerByIdData.getCustomerName().split(" ");
            if (names[0] != null) {
                firstName.setText(names[0]);
            }
            if (names.length == 2 && names[1] != null) {
                lastName.setText(names[1]);
            }
        }
        if (getCustomerByIdData.getBirthday() != null) {
            birthday.setText(getCustomerByIdData.getBirthday());
        }
        if (getCustomerByIdData.getAddress() != null) {
            address.setText(getCustomerByIdData.getAddress());
        }
        if (getCustomerByIdData.getCity() != null) {
            city.setText(getCustomerByIdData.getCity());
        }
        if (getCustomerByIdData.getEmail() != null) {
            email.setText(getCustomerByIdData.getEmail());
        }
        if (getCustomerByIdData.getPincode() != null) {
            pincode.setText(getCustomerByIdData.getPincode());
        }
        if (getCustomerByIdData.getNote() != null) {
            note.setText(getCustomerByIdData.getNote());
        }
        if (getCustomerByIdData.getMobilePhone() != null) {
            mobile.setText(getCustomerByIdData.getMobilePhone());
        }

        addCustomerButton.setText("Edit Customer");
        NavigationActivity.isInProgress = false;
        progressBar.setVisibility(View.GONE);

    }
}
