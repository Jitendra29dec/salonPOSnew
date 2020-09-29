package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hubwallet.apptspos.Adapters.AdapterCategory;
import com.hubwallet.apptspos.Adapters.AdapterProduct;
import com.hubwallet.apptspos.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class itemForListFragment1 extends Fragment {
    private static AdapterProduct adapter;
    private static AdapterCategory cadapter;

    public static void messageGet(String sd) {
        Log.e("messageGet: ", sd);
        if (sd.equalsIgnoreCase("product")) {
            adapter.count();
        } else {
            cadapter.count();
            Log.e("messageGet: ", "c");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_for_listfragment_1, null, false);
        Bundle v = getArguments();
        RecyclerView recyclerView = view.findViewById(R.id.recylerVeiwItemFrag);
        //vds=v.getString("type");
        adapter = new AdapterProduct(getContext(), v.getString("type"));
        cadapter = new AdapterCategory(getContext(), v.getString("type"));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (v.getString("type").equalsIgnoreCase("c")) {
            recyclerView.setAdapter(cadapter);

        } else {
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}
