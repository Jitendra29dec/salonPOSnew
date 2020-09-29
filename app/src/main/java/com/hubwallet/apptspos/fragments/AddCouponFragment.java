package com.hubwallet.apptspos.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddCategory;
import com.hubwallet.apptspos.APis.AddCoupon;
import com.hubwallet.apptspos.APis.EditCategory;
import com.hubwallet.apptspos.APis.EditCoupon;
import com.hubwallet.apptspos.APis.GetCategoryById;
import com.hubwallet.apptspos.APis.GetCouponById;
import com.hubwallet.apptspos.APis.GetTemplate;
import com.hubwallet.apptspos.APis.GetVendor;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.CouponById.GetCouponData;
import com.hubwallet.apptspos.Utils.Models.GetCategoryByData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierData;
import com.hubwallet.apptspos.Utils.Models.GetSupplier.SupplierList;
import com.hubwallet.apptspos.Utils.Models.GetTemplateData;
import com.hubwallet.apptspos.Utils.Models.GetTemplateList;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class AddCouponFragment extends Fragment implements ApiCommunicator, View.OnClickListener {
    private AppCompatTextView submitBtn, backBtn, btnCoupon;
    private TextView etValidFrom, etValidTill, startTime, endTime;
    private RadioGroup rgDiscountType;
    private String discountType = "2";
    private RadioButton btnFlat, btnPercentage;
    private EditText amount, description, coupon;
    private Communicator communicator;
    ApiCommunicator apiCommunicator;
    private boolean isForEdit = false;
    private String startDate, endDate;
    private String from, to;
    private Spinner spnCouponFor, spnTemplate;
    private ArrayList<String> discountForList = new ArrayList<>();
    private String couponId = "";
    AppCompatTextView couponTitle;
    private Calendar calendar;
    String couponFor;
    private List<GetTemplateData> templateData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            couponId = getArguments().getString("coupon_id", "");
            if (!couponId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", couponId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_coupon, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        calendar = Calendar.getInstance();
        couponTitle = view.findViewById(R.id.couponTitle);
        amount = view.findViewById(R.id.amountEdiText);
        etValidFrom = view.findViewById(R.id.validFromEditText);
        etValidTill = view.findViewById(R.id.validTillEditText);
        startTime = view.findViewById(R.id.startTimeEditText);
        endTime = view.findViewById(R.id.endTimeEditText);
        description = view.findViewById(R.id.descriptionEditText);
        btnCoupon = view.findViewById(R.id.btnCoupon);
        coupon = view.findViewById(R.id.coupon);
        rgDiscountType = view.findViewById(R.id.rGDiscountType);
        btnFlat = view.findViewById(R.id.rBtnFlat);
        btnPercentage = view.findViewById(R.id.rBtnPercentage);
        spnCouponFor = view.findViewById(R.id.couponForSpinner);
        submitBtn = view.findViewById(R.id.submitButton);
        backBtn = view.findViewById(R.id.imgBack);
        spnTemplate = view.findViewById(R.id.templateSpinner);
        MySingleton.getInstance(getContext()).addToRequestQue(new GetTemplate(apiCommunicator, new PrefManager(getContext()).getVendorId()).getStringRequest());
        etValidFrom.setOnClickListener(this);
        etValidTill.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);

        discountForList.add("SELECT");
        discountForList.add("Product");
        discountForList.add("Service");
        setSpinner(discountForList, spnCouponFor);
      /*  ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, discountForList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCouponFor.setAdapter(adapter);
*/

        if (isForEdit) {
            couponTitle.setText("Edit Coupon");
            submitBtn.setText("Update");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new CouponFragment())
                        .commit();
            }
        });

        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                String randomNumber = String.format("%04d", r.nextInt(1001));
                coupon.setText(randomNumber);
            }
        });


        submitBtn.setOnClickListener(view1 -> {
            if (!amount.getText().toString().isEmpty() && amount.getText().toString().length() > 0) {
                if (btnFlat.isChecked()) {
                    discountType = "1";
                } else {
                    discountType = "2";
                }


                String templateId;
                int couponFor;
                couponFor = spnCouponFor.getSelectedItemPosition();
                templateId = templateData.get(spnTemplate.getSelectedItemPosition() != 0 ? spnTemplate.getSelectedItemPosition() - 1 : spnTemplate.getSelectedItemPosition()).getTemplateId();
                if (isForEdit) {
                    MySingleton.getInstance(getContext())
                            .addToRequestQue(new EditCoupon(new PrefManager(getContext())
                                    .getVendorId(), couponId, String.valueOf(couponFor), discountType, amount.getText().toString(), "", startDate, endDate,
                                    description.getText().toString(), coupon.getText().toString(), "", from, to, templateId, apiCommunicator).getStringRequest());
                } else {
                    MySingleton.getInstance(getContext())
                            .addToRequestQue(new AddCoupon(new PrefManager(getContext())
                                    .getVendorId(), String.valueOf(couponFor), discountType, amount.getText().toString(), "", startDate, endDate, description.getText().toString(),
                                    coupon.getText().toString(), "", from, to, templateId, apiCommunicator).getStringRequest());
                }

            } else {
                amount.setError("Enter Amount");
            }
        });
        if (isForEdit) {
            MySingleton.getInstance(getContext()).addToRequestQue(new GetCouponById(apiCommunicator, new PrefManager(getContext()).getVendorId(), couponId).getStringRequest());
        }

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("get_template")) {
            templateData = new GsonBuilder().create().fromJson(response, GetTemplateList.class).getResult();
            ArrayList<String> list = new ArrayList<>();
            list.add("SELECT");
            for (GetTemplateData b : templateData) {
                list.add(b.getTemplateName());
            }
            setSpinner(list, spnTemplate);
        }
        if (status.equalsIgnoreCase("get_coupon_byId")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetCouponData couponData = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetCouponData.class);
                updateCoupon(couponData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (status.equalsIgnoreCase("coupon_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("coupon_added", "");
        }

        if (status.equalsIgnoreCase("coupon_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("coupon_added", "");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.validFromEditText:
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    startDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    etValidFrom.setText(startDate);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;

            case R.id.validTillEditText:
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(getContext(), (view1, year1, monthOfYear, dayOfMonth) -> {
                    endDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    etValidTill.setText(endDate);
                }, year, month, day);
                datePickerDialog1.show();
                break;
            case R.id.startTimeEditText:
                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        (view12, hourOfDay, minute) -> {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
                            try {
                                Date date = simpleDateFormat.parse(hourOfDay + ":" + minute);
                                from = simpleDateFormat.format(date);
                                startTime.setText(from);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.endTimeEditText:
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog1 = new TimePickerDialog(getContext(),
                        (view12, hourOfDay, minute1) -> {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
                            try {
                                Date date = simpleDateFormat.parse(hourOfDay + ":" + minute1);
                                to = simpleDateFormat.format(date);
                                endTime.setText(to);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }, hour, minute, false);
                timePickerDialog1.show();
                break;
        }
    }

    private void updateCoupon(GetCouponData couponData) {
        amount.setText(couponData.getDiscount());
        coupon.setText(couponData.getCouponCode());
        description.setText(couponData.getDescription());
        etValidFrom.setText(couponData.getStartDate().split(" ")[0]);
        etValidTill.setText(couponData.getEndDate().split(" ")[0]);
        startTime.setText(couponData.getStartTime());
        endTime.setText(couponData.getEndTime());
        if (couponData.getCouponFor().equalsIgnoreCase("1")) {
            spnCouponFor.setSelection(1);
        } else if (couponData.getCouponFor().equalsIgnoreCase("2")) {
            spnCouponFor.setSelection(2);
        }
        if (templateData!=null){
            for (int i = 0; i < templateData.size(); i++) {
                if (templateData.get(i).getTemplateId().equalsIgnoreCase(couponData.getTemplateId())) {
                    spnTemplate.setSelection(i + 1);
                }

            }
        }
        if (couponData.getCoupon_type().equalsIgnoreCase("1")) {
            btnPercentage.setChecked(true);
        } else {
            btnFlat.setChecked(true);
        }

    }

    private void setSpinner(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }

}
