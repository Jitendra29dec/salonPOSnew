package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PersonalTaskRecyclerViewAdapter extends RecyclerView.Adapter<Type> {
    List<GetStylistData> stylistList;
    private Context context;
    private ApiCommunicator apiCommunicator;

    public PersonalTaskRecyclerViewAdapter(List<GetStylistData> stylistList, Context context, ApiCommunicator apiCommunicator) {
        this.stylistList = stylistList;
        this.context = context;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public Type onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.personal_task_recycler_view_stylist_item, null, false);
        ViewGroup.LayoutParams llp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(llp);

        return new Type(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Type holder, final int position) {
        holder.taskType.setText(stylistList.get(position).getStylistName());
        holder.taskType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("stylistId", stylistList.get(position).getStylistId());
                    jsonObject.put("stylist", stylistList.get(position).getStylistName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiCommunicator.getApiData("stylist_selected_personal", jsonObject.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stylistList.size();
    }
}

class StylistElement extends RecyclerView.ViewHolder {
    TextView stylistName;

    public StylistElement(@NonNull View itemView) {
        super(itemView);
        stylistName = itemView.findViewById(R.id.stylistNameElementPersonalTalkRecyclerVIew);
    }
}