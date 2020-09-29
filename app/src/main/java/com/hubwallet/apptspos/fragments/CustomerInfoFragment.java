package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.hubwallet.apptspos.Adapters.viewPagerAdapter;
import com.hubwallet.apptspos.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class CustomerInfoFragment extends DialogFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onStart() {
        super.onStart();
        int dialogWidth = getResources().getDimensionPixelSize(R.dimen._220sdp);
        int dialogHeight = getResources().getDimensionPixelSize(R.dimen._260sdp);
        if (getDialog().getWindow() == null) return;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customer_info_dialog, null, false);
        tabLayout = v.findViewById(R.id.tabLayoutCustomerInfoDialog);
        viewPager = v.findViewById(R.id.viewPagerCustomerInfoDialog);
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                list.add(new ProfileFragmentCustomer());
            }
            if (i == 1) {
                list.add(new DataFragmentCustomerInfo());
            }
            if (i == 2) {
                list.add(new AppointmentsCustomerInfoFragments());
            }
        }
        viewPager.setAdapter(new viewPagerAdapter(getChildFragmentManager(), list));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }

}
