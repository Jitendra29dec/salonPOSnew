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

public class SalesSwipeFragment extends Fragment {
    private ViewPager swipeView;
    private ArrayList<Fragment> list;
    private SalesLeftSide fragment1;
    private CheckoutRightSide fragment2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_swipe_view, container, false);
        list = new ArrayList<>();
        assert getArguments() != null;
        fragment1 = new SalesLeftSide();
        fragment2 = new CheckoutRightSide();
        swipeView = v.findViewById(R.id.viewPagerForCheckOut);
        list.add(fragment1);
        Bundle bundle = new Bundle();
        bundle.putString("fragmentType", "sales");
        fragment2.setArguments(bundle);
        list.add(fragment2);
        swipeView.setAdapter(new viewPagerForCheckOutAdapter(getChildFragmentManager(), list));
        return v;
    }


    public void updateForSale(String res) {
        fragment2.updateForSales(res);

    }

    public void updatePage(int val) {
        swipeView.setCurrentItem(val, true);
    }

}
