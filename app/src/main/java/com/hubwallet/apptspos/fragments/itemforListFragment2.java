package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hubwallet.apptspos.R;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class itemforListFragment2 extends Fragment {
    public static Bus bus = new Bus(ThreadEnforcer.MAIN);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_for_listfragment_2, null, false);
        Button add = v.findViewById(R.id.addItem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemForListFragment1.messageGet("Product");
            }
        });
        return v;
    }
}
