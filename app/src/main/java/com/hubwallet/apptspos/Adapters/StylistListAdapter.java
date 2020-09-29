package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData;
import com.hubwallet.apptspos.customer.CustomerClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StylistListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    private List<GetStylistData> list;
    private List<GetStylistData> FilterList;
    private CustomerClickListener customerClickListener;
    private ApiCommunicator apiCommunicator;

    private int empImgArr[] = {
            R.drawable.emp1,
            R.drawable.emp2,
            R.drawable.emp3,
            R.drawable.emp4,
            R.drawable.emp5,
            R.drawable.emp6,
            R.drawable.emp7,
            R.drawable.emp8,
            R.drawable.emp9,
            R.drawable.emp10
    };

    public StylistListAdapter(Context context, List<GetStylistData> list, ApiCommunicator apiCommunicator, CustomerClickListener customerClickListener) {
        this.context = context;
        this.list = list;
        this.FilterList = list;
        this.apiCommunicator = apiCommunicator;
        this.customerClickListener = customerClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
//        if (i == -1) {
//            View v = LayoutInflater.from(context).inflate(R.layout.layout_stylist__item, null, false);
//            v.setLayoutParams(lp);
//            return new StylistHeader(v);
//        }
        View v = LayoutInflater.from(context).inflate(R.layout.layout_stylist_data_item, null, false);
        v.setLayoutParams(lp);
        return new StylistHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) != -1) {
        StylistHolder stylistHolder = (StylistHolder) holder;
        if (FilterList.get(position).getPhoto() != null
                && !FilterList.get(position).getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(FilterList.get(position).getPhoto())
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(stylistHolder.imgEmployee);
        } else {
            Glide.with(context)
                    .load(R.drawable.place_holder_1)
                    .into(stylistHolder.imgEmployee);
        }

        stylistHolder.phone.setText(FilterList.get(position).getPhone());
        stylistHolder.email.setText(FilterList.get(position).getEmail());
        stylistHolder.employeeName.setText(FilterList.get(position).getStylistName());
        stylistHolder.tittle.setText("Stylist");
        stylistHolder.status.setText(FilterList.get(position).getStatus());

        stylistHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerClickListener.onClickCustItem(FilterList.get(position).getStylistId());
            }
        });
      //  stylistHolder.imgEmployee.setImageResource(empImgArr[new Random().nextInt(empImgArr.length)]);

//        }
    }

    /*   @Override
       public void onBindViewHolder(@NonNull StylistHolder viewHolder, int i) {
           if (FilterList.get(i).getPhoto() != null) {
               Glide.with(context).load(FilterList.get(i).getPhoto()).into(viewHolder.circleImageView);
           }
           viewHolder.name.setText(FilterList.get(i).getStylistName());
           final int pos = i;
           viewHolder.close.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                   alertDialog.setMessage("Are you sure?");
                   alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           MySingleton.getInstance(context).addToRequestQue(new DeleteStylist(new PrefManager(context).getVendorId(), FilterList.get(pos).getStylistId(), apiCommunicator).getStringRequest());

                       }
                   });
                   alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           alertDialog.dismiss();
                       }
                   });
                   alertDialog.show();
               }
           });
           viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (!NavigationActivity.isInProgress) {
                       apiCommunicator.getApiData("stylist_by_id", FilterList.get(pos).getStylistId());
                   }
               }
           });
       }*/
    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return -1;
//        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return FilterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<GetStylistData> tempList = new ArrayList<>();
                if (constraint.toString().equalsIgnoreCase("")) {
                    tempList = list;
                } else {
                    for (GetStylistData data : list) {
                        if (data.getStylistName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(data);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.count = tempList.size();
                results.values = tempList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null) {
                    FilterList = (List<GetStylistData>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}

class StylistHeader extends RecyclerView.ViewHolder {
    public StylistHeader(@NonNull View itemView) {
        super(itemView);
    }
}

class StylistHolder extends RecyclerView.ViewHolder {
    TextView employeeName, tittle, email, phone, status, calendar;
    ImageView imgEmployee;
    LinearLayout parentLayout;

    StylistHolder(@NonNull View itemView) {
        super(itemView);
        parentLayout = itemView.findViewById(R.id.parentLayout);
        employeeName = itemView.findViewById(R.id.employeeNameField);
        tittle = itemView.findViewById(R.id.titleField);
        email = itemView.findViewById(R.id.emailField);
        phone = itemView.findViewById(R.id.phoneField);
        status = itemView.findViewById(R.id.statusField);
        imgEmployee = itemView.findViewById(R.id.imgEmployee);

    }
}