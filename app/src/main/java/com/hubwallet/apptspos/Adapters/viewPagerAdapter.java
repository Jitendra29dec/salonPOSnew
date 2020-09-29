package com.hubwallet.apptspos.Adapters;

import android.util.Log;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;

    public viewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        Log.e("viewPagerAdapter: ", "called");
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }


    @Override
    public int getCount() {
        return list.size();
    }
}
