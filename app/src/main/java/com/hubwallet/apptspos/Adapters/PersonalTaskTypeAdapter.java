package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.PersonalTaskTypeModel.PersonalTaskTypeData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PersonalTaskTypeAdapter extends RecyclerView.Adapter<Type> {
    List<PersonalTaskTypeData> List;
    private Context context;
    private ApiCommunicator apiCommunicator;

    public PersonalTaskTypeAdapter(List<PersonalTaskTypeData> List, Context context, ApiCommunicator apiCommunicator) {
        this.List = List;
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
        holder.taskType.setText(List.get(position).getName());
        holder.taskType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", List.get(position).getTypeId());
                    jsonObject.put("name", List.get(position).getName());
                    apiCommunicator.getApiData("personal_task_type_input", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}

class Type extends RecyclerView.ViewHolder {
    TextView taskType;

    public Type(@NonNull View itemView) {
        super(itemView);
        taskType = itemView.findViewById(R.id.stylistNameElementPersonalTalkRecyclerVIew);
    }
}