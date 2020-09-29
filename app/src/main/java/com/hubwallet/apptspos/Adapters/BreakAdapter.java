package com.hubwallet.apptspos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.BreakData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreakAdapter extends RecyclerView.Adapter<BreakAdapter.MyViewHolder> {
    private Context context;
    List<BreakData> arrayListCallBack;
    ApiCommunicator communicator;
  //  private CommanInterface commanInterface;

    public BreakAdapter(Context activity, List<BreakData> arrayList,ApiCommunicator communicator) {
        this.context = activity;
        this.communicator = communicator;
        this.arrayListCallBack = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.break_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position %2==1){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#f2f6f7"));
        }
        holder.textOne.setText(arrayListCallBack.get(position).getAttendanceTime());
        holder.textTwo.setText(arrayListCallBack.get(position).getAttendenceBackTime());
        holder.textThree.setText(arrayListCallBack.get(position).getTotalHours());
     //   holder.textFour.setText(arrayListCallBack.get(position).getTotalHours());

    }

    @Override
    public int getItemCount() {
        return arrayListCallBack.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textOne)
        TextView textOne;
        @BindView(R.id.textTwo)
        TextView textTwo;
        @BindView(R.id.textThree)
        TextView textThree;
        @BindView(R.id.textFour)
        TextView textFour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /*public void setCallInterface(CommanInterface commanInterface1) {
        this.commanInterface = commanInterface1;
    }*/
}