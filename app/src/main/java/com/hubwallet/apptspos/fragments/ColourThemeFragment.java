package com.hubwallet.apptspos.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.Adapters.ColourthemeAdapter;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ColorPikInterface;
import com.hubwallet.apptspos.Utils.CommanInterface;
import com.hubwallet.apptspos.Utils.SpacesItemDecoration;

import java.util.ArrayList;

public class ColourThemeFragment extends Fragment implements ColorPikInterface {
    private RecyclerView recyclerView;
    private ArrayList<Integer> colourList = new ArrayList<>();
    ColourthemeAdapter adapter;
    LinearLayout header;

    //hhh
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_colour_theme, container, false);
        recyclerView = view.findViewById(R.id.colourList);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));
        header = ((NavigationActivity) this.getActivity()).findViewById(R.id.headerBg);
        //   ColourModel model = new ColourModel();
        //   model.setColour(Color.parseColor("#fe0000"));


        colourList.add(Color.parseColor("#fe0000"));
        colourList.add(Color.parseColor("#0000fe"));
        colourList.add(Color.parseColor("#fea500"));
        colourList.add(Color.parseColor("#008001"));

        colourList.add(Color.parseColor("#ee82ef"));
        colourList.add(Color.parseColor("#ffc0cb"));
        colourList.add(Color.parseColor("#556574"));
        colourList.add(Color.parseColor("#6f2c01"));

        colourList.add(Color.parseColor("#5599c8"));
        colourList.add(Color.parseColor("#8d44ad"));
        colourList.add(Color.parseColor("#f1c40f"));
        colourList.add(Color.parseColor("#171f2a"));
        colourList.add(Color.parseColor("#ffffff"));

        adapter = new ColourthemeAdapter(getContext(), colourList, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view, int position) {
        if (position == 0) {
            header.setBackgroundColor(Color.parseColor("#fe0000"));
        } else if (position == 1) {
            header.setBackgroundColor(Color.parseColor("#0000fe"));
        } else if (position == 2) {
            header.setBackgroundColor(Color.parseColor("#fea500"));
        } else if (position == 3) {
            header.setBackgroundColor(Color.parseColor("#008001"));
        } else if (position == 4) {
            header.setBackgroundColor(Color.parseColor("#ee82ef"));
        } else if (position == 5) {
            header.setBackgroundColor(Color.parseColor("#ffc0cb"));
        } else if (position == 6) {
            header.setBackgroundColor(Color.parseColor("#556574"));
        } else if (position == 7) {
            header.setBackgroundColor(Color.parseColor("#6f2c01"));
        } else if (position == 8) {
            header.setBackgroundColor(Color.parseColor("#5599c8"));
        } else if (position == 9) {
            header.setBackgroundColor(Color.parseColor("#8d44ad"));
        } else if (position == 10) {
            header.setBackgroundColor(Color.parseColor("#f1c40f"));
        } else if (position == 11) {
            header.setBackgroundColor(Color.parseColor("#171f2a"));
        }else if (position == 12) {
            header.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void updateColor(View view, int position,String colorId) {

    }
}
