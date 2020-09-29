package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hubwallet.apptspos.Adapters.viewPagerForCheckOutAdapter;
import com.hubwallet.apptspos.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class SwipeFragment extends Fragment {
    private ViewPager swipeView;
    private ArrayList<Fragment> list;
    private CheckoutLeftSide fragment1;
    private CheckoutRightSide fragment2;
    private viewPagerForCheckOutAdapter adapter;

    @Nullable


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_swipe_view, container, false);
        list = new ArrayList<>();
        assert getArguments() != null;
        String id = getArguments().getString("appointment_id");
        fragment1 = new CheckoutLeftSide();
        fragment2 = new CheckoutRightSide();
        Bundle bundle = new Bundle();
        bundle.putString("appointment_id", id);
        fragment2.setArguments(bundle);
        fragment1.setArguments(bundle);
        swipeView = v.findViewById(R.id.viewPagerForCheckOut);
        list.add(fragment1);
        list.add(fragment2);
        adapter = new viewPagerForCheckOutAdapter(getChildFragmentManager(), list);
        swipeView.setAdapter(adapter);
        return v;
    }

    public void updatePage(int val) {
        swipeView.setCurrentItem(val, true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        swipeView = null;
        fragment2 = null;
        fragment1 = null;
    }
}

