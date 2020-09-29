package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hubwallet.apptspos.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class itemforDiscountFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_for_discountfragment_2, null, false);
        Button a = v.findViewById(R.id.fragmentButtonAdd);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemForListFragment1.messageGet("product");
            }
        });
        return v;
    }
}
