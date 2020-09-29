package com.hubwallet.apptspos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.messaging.FirebaseMessaging;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.PrefManager;
import com.hubwallet.apptspos.base.BaseViewModel;
import com.hubwallet.apptspos.retrofit.login.PinLoginRes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ApiCommunicator {
    String username, password = "";
    EditText usernameEditText, passwordEditText;
    ApiCommunicator apiCommunicator;
    BaseViewModel viewModel;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager = new PrefManager(this);
        FirebaseMessaging.getInstance();
        initialize();
        Button loginButton = findViewById(R.id.loginButton);

//
//        usernameEditText.setText("admin");
//        passwordEditText.setText("bnp@2020$$");
        viewModel = new ViewModelProvider(this).get(BaseViewModel.class);


        loginButton.setOnClickListener(v -> {

            username = usernameEditText.getText().toString();
            password = passwordEditText.getText().toString();

            String fcmtoken = prefManager.getData(PrefManager.FCMTOKEN);
            if (!password.isEmpty()) {
                loginPin(password, fcmtoken);
            } else {
                passwordEditText.setError("Field can't be empty");
            }
//                if (!username.isEmpty()) {
//                    if (!password.isEmpty()) {
//                        MySingleton.getInstance(MainActivity.this)
//                                .addToRequestQue(new LoginApi(username, password, apiCommunicator)
//                                        .getStringRequest());
//                    } else {
//                        passwordEditText.setError("Field can't be empty");
//                    }
//                } else {
//                    usernameEditText.setError("Field can't be empty");
//                }

        });
    }

    private void initialize() {
        usernameEditText = findViewById(R.id.usernameField);
        passwordEditText = findViewById(R.id.passwordField);
        apiCommunicator = this;
    }


    private void loginPin(String pin, String fcm_token) {

        viewModel.loginPin(pin, fcm_token);
        Objects.requireNonNull(viewModel.getPinLoginLiveData()).observe(this, new Observer<PinLoginRes>() {
            @Override
            public void onChanged(PinLoginRes baseResponse) {
                if (baseResponse.getStatus() == 1) {
                    PrefManager manager = new PrefManager(MainActivity.this);
                    manager.setLogiId(baseResponse.getData().getLoginId());
                    manager.setVendorId(baseResponse.getData().getVendorId());
                    manager.setVendorName(baseResponse.getData().getVendorName());
                    Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("1")) {
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);

            try {
                JSONObject jsonObject = new JSONObject(response);
                PrefManager manager = new PrefManager(this);
                JSONObject detail = new JSONObject(jsonObject.getString("data"));

                manager.setLogiId(detail.getString("login_id"));
                manager.setVendorId(detail.getString("vendor_id"));
                manager.setVendorName(detail.getString("vendor_name"));
                manager.setData(response);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(intent);
            finish();
        }
        if (status.equalsIgnoreCase("toast")) {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        }
    }
}
