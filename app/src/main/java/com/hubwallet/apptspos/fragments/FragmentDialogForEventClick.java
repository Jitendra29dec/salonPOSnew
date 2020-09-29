package com.hubwallet.apptspos.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.FragmentsComunicator;
import com.hubwallet.apptspos.calander.change_Status.ChangeStatuskFragment;
import com.hubwallet.apptspos.checkout.CheckoutFragment;

import java.util.Objects;

public class FragmentDialogForEventClick extends DialogFragment {

    private ChangeStatuskFragment fragment;
    private FragmentsComunicator sendDataToCalendarFragment;

    @Override
    public void onStart() {
        super.onStart();
        int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.5f);
      //  int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.82f);
        if (Objects.requireNonNull(getDialog()).getWindow() == null) return;
        Objects.requireNonNull(getDialog().getWindow()).setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        Objects.requireNonNull(getDialog()).requestWindowFeature(Window.FEATURE_NO_TITLE);
        LinearLayout edit = view.findViewById(R.id.editLinearLayout);

        RelativeLayout notes = view.findViewById(R.id.notesLinearLayout);
        TextView customerName = view.findViewById(R.id.customerNAmePopup);
       // TextView serviceName = view.findViewById(R.id.serviceNamePopup);
        assert getArguments() != null;
        customerName.setText(getArguments().getString("name"));
//        serviceName.setText(getArguments().getString("service"));
        LinearLayout changeStatus = view.findViewById(R.id.changeStatusLinearLayout);
     //   LinearLayout containerLayout = view.findViewById(R.id.containerStatus);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button checkOutButton = view.findViewById(R.id.checkOutButton);
       // RelativeLayout customerInfoLayout = view.findViewById(R.id.customerInfoBar);
        RelativeLayout rebook = view.findViewById(R.id.rebookLinearLayout);
        fragment = new ChangeStatuskFragment();
        sendDataToCalendarFragment = (FragmentsComunicator) getTargetFragment();
      /*  customerInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendDataToCalendarFragment.message("customer_info");
            }
        });*/
        btnCancel.setOnClickListener(view1 -> dismiss());
        rebook.setOnClickListener(view12 -> sendDataToCalendarFragment.message("rebook"));
        checkOutButton.setOnClickListener(v -> {
            CheckoutFragment fragment = CheckoutFragment.Companion.newInstance();
            fragment.setArguments(getArguments());
            NavigationActivity navigationActivity = (NavigationActivity) requireActivity();
            navigationActivity.curretItem = 5;
            NavigationActivity.fm.beginTransaction().replace(R.id.containerMain, fragment).commit();
            dismiss();
        });
        edit.setOnClickListener(v -> sendDataToCalendarFragment.message("edit"));
        changeStatus.setOnClickListener(v -> {
            fragment.setArguments(getArguments());
            fragment.setTargetFragment(FragmentDialogForEventClick.this, 9);
            fragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "dialogStatus");
//               getParentFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.enter_from_right, R.anim.enter_from_right)
//                        .replace(R.id.containerStatus, fragment)
//                        .commit();
        });
        notes.setOnClickListener(view13 -> sendDataToCalendarFragment.message("notes"));

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent();

        Objects.requireNonNull(getTargetFragment()).onActivityResult(9, Activity.RESULT_OK, i);
        dismiss();
    }
}
