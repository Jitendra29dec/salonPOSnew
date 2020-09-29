package com.hubwallet.apptspos.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.BreakOnClick;
import com.hubwallet.apptspos.Utils.CommanInterface;
import com.hubwallet.apptspos.Utils.Models.BreakLeave;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogBreakAdapter  extends RecyclerView.Adapter<DialogBreakAdapter.MyViewHolder> {
    private Context context;
    List<BreakLeave> arrayListCallBack;
    private CommanInterface commanInterface;
    private ApiCommunicator communicator;
    BreakOnClick breakOnClick;

    public DialogBreakAdapter(Context activity, List<BreakLeave> breakLeaveArrayList, ApiCommunicator communicator,BreakOnClick breakOnClick) {
        this.context = activity;
        this.arrayListCallBack = breakLeaveArrayList;
        this.communicator = communicator;
        this.breakOnClick = breakOnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_break_time, parent, false);
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
        holder.textOne.setText(arrayListCallBack.get(position).getAttendanceDate());
     //   holder.textTwo.setText(arrayListCallBack.get(position).getTotalBreak());
        holder.textThree.setText(arrayListCallBack.get(position).getTotalBreak());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                breakOnClick.breakClick(view,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListCallBack.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textOne)
        TextView textOne;
       /* @BindView(R.id.textTwo)
        TextView textTwo;*/
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