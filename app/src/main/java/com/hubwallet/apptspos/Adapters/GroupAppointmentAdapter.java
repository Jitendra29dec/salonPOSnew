package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.GroupAppointmentInterface;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomerData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class GroupAppointmentAdapter extends RecyclerView.Adapter<GroupAppointmentViewHolder> {
    private Context context;
    private List<GetCustomerData> list;
    private GroupAppointmentInterface groupAppointmentInterface;

    public GroupAppointmentAdapter(Context context, List<GetCustomerData> list, GroupAppointmentInterface groupAppointmentInterface) {
        this.context = context;
        this.list = list;
        this.groupAppointmentInterface = groupAppointmentInterface;
    }

    @NonNull
    @Override
    public GroupAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_appointment_layout_recycler_view, parent, false);
        return new GroupAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupAppointmentViewHolder holder, final int position) {
        String name;
        if (list.get(position).isCaptain()) {
//            holder.starButton.setVisibility(View.VISIBLE);
            holder.starButton.setImageResource(R.drawable.ic_star_fill_yellow);
        } else {
//            holder.starButton.setVisibility(View.GONE);
            holder.starButton.setImageResource(R.drawable.ic_star_unfilled_grey);
        }

//        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(context, holder.layout);
//                popupMenu.inflate(R.menu.popupmenu);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        for (int i = 0; i < list.size(); i++) {
//                            GetCustomerData data = list.get(i);
//                            if (data.isCaptain()) {
//                                list.get(i).setCaptain(false);
//                                notifyItemChanged(i);
//                            }
//                        }
//                        list.get(position).setCaptain(true);
//                        notifyDataSetChanged();
//                        return true;
//                    }
//                });
//                popupMenu.show();
//                return true;
//            }
//        });

        if (list.get(position).isSelected()) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.very_light_grey));
        } else {
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.trasparent));
        }

//        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.get(position).setCaptain(false);
//                if (list.get(position).isSelected()) {
//                    list.get(position).setSelected(false);
//                }
//                groupAppointmentInterface.groupListPos(position, "deleted");
//                list.remove(position);
//                if (getItemCount() == 0) {
//                    groupAppointmentInterface.emptyCustomerListNotify();
//                }
//                notifyDataSetChanged();
//            }
//        });

        name = list.get(position).getCustomerName().split("\n")[0];
        holder.CustomerName.setText(name);

        holder.layout.setOnClickListener(v -> {
            for (int i = 0; i < list.size(); i++) {
                GetCustomerData data = list.get(i);
                if (data.isSelected()) {
                    list.get(i).setSelected(!data.isSelected());
                }
            }
            list.get(position).setSelected(!list.get(position).isSelected());
            notifyDataSetChanged();
            groupAppointmentInterface.groupListPos(position, "selected");
        });

        holder.starButton.setOnClickListener(view -> {
            for (int i = 0; i < list.size(); i++) {
                GetCustomerData data = list.get(i);
                if (data.isCaptain()) {
                    list.get(i).setCaptain(false);
                }
            }
            list.get(position).setCaptain(true);
            notifyDataSetChanged();
        });

        holder.imgBtnShowOption.setOnClickListener(view -> {
            ListPopupWindow popupWindow = new ListPopupWindow(context);
            List<String> popupList = new ArrayList<>();
            popupList.add("Delete");
            popupList.add("Add Note");
            popupWindow.setAdapter(new ArrayAdapter<>(context, R.layout.li_spin_search, popupList));
            popupWindow.setAnchorView(holder.imgBtnShowOption);
            popupWindow.setWidth(100);
            popupWindow.setOnItemClickListener((adapterView, view1, i, l) -> {
                popupWindow.dismiss();
                if (i == 0) {
                    list.get(position).setCaptain(false);
                    if (list.get(position).isSelected()) {
                        list.get(position).setSelected(false);
                    }
                    groupAppointmentInterface.groupListPos(position, "deleted");
                    list.remove(position);
                    if (getItemCount() == 0) {
                        groupAppointmentInterface.emptyCustomerListNotify();
                    }
                    notifyDataSetChanged();
                } else {

                }
            });
            popupWindow.show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(GetCustomerData data) {
        boolean isPresent = false;
        for (GetCustomerData x : list) {
            if (x.getCustomerId().equalsIgnoreCase(data.getCustomerId())) {
                isPresent = true;
            }
        }
        if (!isPresent) {
            list.add(data);
            groupAppointmentInterface.groupAppointmentCustomerData(data, "added");
        } else {
            Toast.makeText(context, "Customer already selected! ", Toast.LENGTH_SHORT).show();
        }

        notifyDataSetChanged();
    }

    public GetCustomerData getCustomer(int pos) {
        return list.get(pos);
    }

    public List<GetCustomerData> getCustomerlist() {
        return list;
    }

    public void setList(List<GetCustomerData> data) {
        list.clear();
        list = data;
        notifyDataSetChanged();
    }
}

class GroupAppointmentViewHolder extends RecyclerView.ViewHolder {
    TextView CustomerName;
    ImageButton deleteButton, starButton, imgBtnShowOption;
    LinearLayout layout;

    GroupAppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        deleteButton = itemView.findViewById(R.id.deleteImageButton);
        starButton = itemView.findViewById(R.id.starImageButton);
        imgBtnShowOption = itemView.findViewById(R.id.imgBtnShowOption);
        layout = itemView.findViewById(R.id.constrainLayout);
        CustomerName = itemView.findViewById(R.id.customerNameGroupAppointment);
    }


}