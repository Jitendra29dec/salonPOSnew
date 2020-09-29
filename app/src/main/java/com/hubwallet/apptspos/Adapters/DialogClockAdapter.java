package com.hubwallet.apptspos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.BreakOnClick;
import com.hubwallet.apptspos.Utils.EmployeeOnClick;
import com.hubwallet.apptspos.Utils.Models.ClockIn;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogClockAdapter extends RecyclerView.Adapter<DialogClockAdapter.MyViewHolder> {
    private Context context;
    List<ClockIn> arrayListCallBack;
    private ApiCommunicator communicator;

   // private CommanInterface commanInterface;

    public DialogClockAdapter(Context activity, List<ClockIn> arrayList,ApiCommunicator communicator) {
        this.context = activity;
        this.arrayListCallBack = arrayList;
        this.communicator = communicator;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lunchtime_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position %2==1){
            holder.mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.mainLayout.setBackgroundColor(Color.parseColor("#f2f6f7"));
        }
        holder.textOne.setText(arrayListCallBack.get(position).getClockin());
        holder.textTwo.setText(arrayListCallBack.get(position).getClockout());
        holder.textThree.setText(arrayListCallBack.get(position).getTotalHours());


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
        @BindView(R.id.mainLayout)
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
   /* public void setCallInterface(CommanInterface commanInterface1) {
        this.commanInterface = commanInterface1;
    }*/
}