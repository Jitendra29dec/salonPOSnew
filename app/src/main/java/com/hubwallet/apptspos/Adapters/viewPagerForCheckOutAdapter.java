package com.hubwallet.apptspos.Adapters;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class viewPagerForCheckOutAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> item;

    public viewPagerForCheckOutAdapter(FragmentManager fm, ArrayList<Fragment> item) {
        super(fm);
        this.item = item;
    }

    @Override
    public Fragment getItem(int position) {
        return item.get(position);
    }

    @Override
    public int getCount() {
        return item.size();
    }
}
