package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class StylsitDialogAdapter extends RecyclerView.Adapter<Styllist> {
    private Context context;
    private List<GetStylistData> list;
    private boolean[] isStylistSelected;

    public StylsitDialogAdapter(Context context, List<GetStylistData> list) {
        this.context = context;
        this.list = list;
        this.isStylistSelected = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            isStylistSelected[i] = false;
        }
    }

    @NonNull
    @Override
    public Styllist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.stylist_list_dialog_item, parent, false);
        return new Styllist(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Styllist holder, final int position) {
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isStylistSelected[position] = b;
            }
        });
        holder.name.setText(list.get(position).getStylistName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getSelectedStylist() {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isStylistSelected[i]) {
                ids.add(list.get(i).getStylistId());
            }
        }
        return ids.toString();
    }
}

class Styllist extends RecyclerView.ViewHolder {
    TextView name;
    CheckBox checkBox;

    public Styllist(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.stylistNameDialog);
        checkBox = itemView.findViewById(R.id.stylistCheckboxDialog);
    }
}