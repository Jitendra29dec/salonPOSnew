package com.hubwallet.apptspos.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hubwallet.apptspos.APis.MarkAttendance;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PinCodeActivity extends AppCompatActivity implements ApiCommunicator {

    private EditText pinView1, pinView2, pinView3, pinView4;
    private ApiCommunicator apiCommunicator;
    private String reasons;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        initialize();
        pinView1.requestFocus();
    }


    private void initialize() {
        apiCommunicator = this;
        pinView1 = findViewById(R.id.pinCode1EditText);
        pinView2 = findViewById(R.id.pinCode2EditText);
        pinView3 = findViewById(R.id.pinCode3EditText);
        pinView4 = findViewById(R.id.pinCode4EditText);
    }

    public void setPinFunction(View v) {
        if (v instanceof ImageButton) {
            if (v.getTag().toString().equalsIgnoreCase("okTag")) {
                String pin = pinView1.getText().toString() +
                        pinView2.getText().toString() +
                        pinView3.getText().toString() +
                        pinView4.getText().toString();
                if (pin.length() == 4) {
                    checkPinCode();
                } else
                    Toast.makeText(this, "Please enter correct password!", Toast.LENGTH_SHORT).show();
            } else {
                if (pinView1.hasFocus()) {
                    pinView1.setText("");

                }
                if (pinView2.hasFocus()) {
                    if (!pinView2.getText().toString().equalsIgnoreCase(""))
                        pinView2.setText("");
                    else pinView1.requestFocus();
                }
                if (pinView3.hasFocus()) {
                    if (!pinView3.getText().toString().equalsIgnoreCase(""))
                        pinView3.setText("");
                    else pinView2.requestFocus();
                }
                if (pinView4.hasFocus()) {
                    if (!pinView4.getText().toString().equalsIgnoreCase(""))
                        pinView4.setText("");
                    else pinView3.requestFocus();
                }
            }
        } else {
            TextView r = (TextView) v;
            EditText e = null;
            e = getFoucusedTedVeiw();
            if (e.getText().toString().equalsIgnoreCase("")) {
                e.setText(r.getText().toString());
            }
            requestNextFocus(e);
        }
    }

    private void requestNextFocus(EditText e) {
        if (e.getId() == R.id.pinCode1EditText) {
            pinView2.requestFocus();
        }
        if (e.getId() == R.id.pinCode2EditText) {
            pinView3.requestFocus();
        }
        if (e.getId() == R.id.pinCode3EditText) {
            pinView4.requestFocus();
        }
    }

    private void checkPinCode() {
        final View view = LayoutInflater.from(this).inflate(R.layout.clock_options, null, false);
        Button button = view.findViewById(R.id.submitButtonReason);
        RadioGroup group = view.findViewById(R.id.radioGroupReasons);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pinView1.getText().toString() +
                        pinView2.getText().toString() +
                        pinView3.getText().toString() +
                        pinView4.getText().toString();
                if (pin.length() == 4 && reasons != null && !reasons.isEmpty()) {
                    MySingleton.getInstance(PinCodeActivity.this).addToRequestQue(new MarkAttendance(reasons, pin, new PrefManager(PinCodeActivity.this).getVendorId(), apiCommunicator).getRequest());
                    dialog.dismiss();
                } else {
                    Toast.makeText(PinCodeActivity.this, "Select Reason!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                reasons = radioButton.getTag().toString();
            }
        });
        dialog.show();

    }


    private EditText getFoucusedTedVeiw() {
        EditText editText = null;
        if (pinView1.hasFocus()) {
            editText = pinView1;
        }
        if (pinView2.hasFocus()) {
            editText = pinView2;
        }
        if (pinView3.hasFocus()) {
            editText = pinView3;
        }
        if (pinView4.hasFocus()) {
            editText = pinView4;
        }
        return editText;
    }

    public void ExitFunction(View view) {
        finish();
    }

    @Override
    public void getApiData(String status, String response) {

        try {
            JSONObject object = new JSONObject(response);
            Toast.makeText(this, object.getString("msg"), Toast.LENGTH_SHORT).show();
            if (object.getString("status").equalsIgnoreCase("1")) {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finish();
    }
}