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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.AddCategory;
import com.hubwallet.apptspos.APis.AddDiscount;
import com.hubwallet.apptspos.APis.EditCategory;
import com.hubwallet.apptspos.APis.EditDiscount;
import com.hubwallet.apptspos.APis.GetCouponById;
import com.hubwallet.apptspos.APis.GetDiscountById;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.Models.CouponById.GetCouponData;
import com.hubwallet.apptspos.Utils.Models.GetCategoryByData;
import com.hubwallet.apptspos.Utils.Models.GetDiscountData;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddNewDiscount extends Fragment implements ApiCommunicator {
    private AppCompatTextView submitBtn, backBtn;
    private EditText description, discount, minAMount;
    private Spinner spnDiscountFor;
    private String date;
    private String discountType = "2";
    private RadioButton btnFlat, btnPercentage;
    TextView startDate, endDate;
    private String to;
    private boolean isForEdit = false;
    ApiCommunicator apiCommunicator;
    private Communicator communicator;
    AppCompatTextView DiscountTitle;
    private String discountId = "";
    private ArrayList<String> discountForList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            discountId = getArguments().getString("discount_id", "");
            if (!discountId.isEmpty()) {
                isForEdit = true;
                Log.e("onCreate: ", discountId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_discount, container, false);
        apiCommunicator = this;
        communicator = (Communicator) getActivity();
        backBtn = view.findViewById(R.id.imgBack);
        DiscountTitle = view.findViewById(R.id.discountTitle);
        submitBtn = view.findViewById(R.id.submitButton);
        startDate = view.findViewById(R.id.startDate);
        endDate = view.findViewById(R.id.endDate);
        description = view.findViewById(R.id.descriptionEditText);
        discount = view.findViewById(R.id.discount);
        minAMount = view.findViewById(R.id.minAmountSpent);
        spnDiscountFor = view.findViewById(R.id.discountSpinner);
        btnFlat = view.findViewById(R.id.rFlatDiscount);
        btnPercentage = view.findViewById(R.id.rPercentDiscount);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        discountForList.add("SELECT");
        discountForList.add("Product");
        discountForList.add("Service");
        setSpinner(discountForList, spnDiscountFor);

      /*  ArrayAdapter <String> discountSpn = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, discountForList);
        discountSpn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDiscountFor.setAdapter(discountSpn);*/

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    startDate.setText(date);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
                    endDate.setText(date);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        if (isForEdit) {
            DiscountTitle.setText("Edit Discount");
            submitBtn.setText("Update");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMainSubMenu, new DiscountFragment())
                        .commit();
            }
        });

        submitBtn.setOnClickListener(view1 -> {
            if (btnFlat.isChecked()) {
                discountType = "1";
            } else {
                discountType = "2";
            }
            int couponFor;
            couponFor = spnDiscountFor.getSelectedItemPosition();
            if (isForEdit) {
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new EditDiscount(new PrefManager(getContext())
                                .getVendorId(), discountId, String.valueOf(couponFor), description.getText().toString(), discountType, discount.getText().toString(), startDate.getText().toString(), endDate.toString(), minAMount.getText().toString(), apiCommunicator).getStringRequest());
            } else {
                MySingleton.getInstance(getContext())
                        .addToRequestQue(new AddDiscount(new PrefManager(getContext())
                                .getVendorId(), String.valueOf(couponFor), description.getText().toString(), discountType, discount.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), minAMount.getText().toString(), apiCommunicator).getStringRequest());
            }
        });

        if (isForEdit) {// load data in case everything load up first execute after category as if its last api to load. sot that it should present while putting value on category n brand
            MySingleton.getInstance(getContext()).addToRequestQue(new GetDiscountById(apiCommunicator, new PrefManager(getContext()).getVendorId(), discountId).getStringRequest());
        }

        return view;
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("get_discount_byId")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                GetDiscountData category = new GsonBuilder().create().fromJson(jsonObject.getString("result"), GetDiscountData.class);
                updatate(category);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (status.equalsIgnoreCase("discount_added")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("discount", "");
        }

        if (status.equalsIgnoreCase("discount_edited")) {
            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            communicator.sendmessage("discount", "");
        }
    }

    private void updatate(GetDiscountData discountData) {
        description.setText(discountData.getDescription());
        discount.setText(discountData.getDiscount());
        minAMount.setText(discountData.getMinAmount());
        startDate.setText(discountData.getStartDate().split(" ")[0]);
        endDate.setText(discountData.getEndDate().split(" ")[0]);
        if (discountData.getDiscountFor().equalsIgnoreCase("1")){
            spnDiscountFor.setSelection(1);
        }else if (discountData.getDiscountFor().equalsIgnoreCase("2")){
            spnDiscountFor.setSelection(2);
        }



        /*    duration.setText(services.getDuration());*/
        //  description.setText(services.get);
        //  commission.setText(product.getPurchasePrice());
        /*for (int i = 0; i < brandList.size(); i++) {
            if (brandList.get(i).getBrandId().equalsIgnoreCase(services.)) {
                categorySpinner.setSelection(i);
            }
        }*/

       /* if (product.getBusinessUse().equalsIgnoreCase("1")) {
            //   useForBusinessCheckBok.setChecked(true);
        }*/
    }


    private void setSpinner(ArrayList<String> list, Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.li_spin_search, list));
    }
}
