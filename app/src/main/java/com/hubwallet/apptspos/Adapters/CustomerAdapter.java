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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomerData;
import com.hubwallet.apptspos.customer.CustomerClickListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private Context context;
    private CustomerClickListener customerClickListener;
    private List<GetCustomerData> list;
    private ApiCommunicator apiCommunicator;
    private List<GetCustomerData> FilterList;
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

    public CustomerAdapter(Context context, List<GetCustomerData> list, ApiCommunicator apiCommunicator, CustomerClickListener customerClickListener) {
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
//
//            View v = LayoutInflater.from(context).inflate(R.layout.layout_customer__item, null, false);
//            v.setLayoutParams(lp);
//            return new CustomerHeader(v);
//        }
        View v = LayoutInflater.from(context).inflate(R.layout.layout_customer_data, null, false);
        v.setLayoutParams(lp);
        return new cutomerItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) == -1) {
//        } else {
        cutomerItemHolder holder1 = (cutomerItemHolder) holder;
        if (FilterList.get(position).getPhoto() != null
                && !FilterList.get(position).getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(FilterList.get(position).getPhoto())
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(holder1.imgEmployee);
        } else {
            Glide.with(context)
                    .load(R.drawable.place_holder_1)
                    .into(holder1.imgEmployee);
        }

        holder1.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerClickListener.onClickCustItem(FilterList.get(position).getCustomerId());
            }
        });
        String[] name = FilterList.get(position).getCustomerName().split("\n");
        holder1.name.setText(name[0]);
        holder1.email.setText(FilterList.get(position).getEmail());
        holder1.phone.setText(FilterList.get(position).getMobilePhone());
     //   holder1.imgEmployee.setImageResource(empImgArr[new Random().nextInt(empImgArr.length)]);

//        }
     /*    String[] name = FilterList.get(i).getCustomerName().split("\n");
        cutomerItemHolder.name.setText(name[0]);
        cutomerItemHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NavigationActivity.isInProgress) {
                    apiCommunicator.getApiData("edit_the_customer", FilterList.get(i).getCustomerId());
                    NavigationActivity.isInProgress = true;
                }
            }
        });
        cutomerItemHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MySingleton.getInstance(context).addToRequestQue(new DeleteCustomer(FilterList.get(i).getCustomerId(), new PrefManager(context).getVendorId(), apiCommunicator).getStringRequest());

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

        Glide.with(context).load(FilterList.get(i).getPhoto()).into(cutomerItemHolder.circleImageView);
        if (FilterList.get(i).getPhoto() != null) {
            Glide.with(context).load(list.get(i).getPhoto()).into(cutomerItemHolder.circleImageView);
        }*/
    }

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
                List<GetCustomerData> tempList = new ArrayList<>();
                if (constraint.toString().equalsIgnoreCase("")) {
                    tempList = list;
                } else {
                    for (GetCustomerData data : list) {
                        if (data.getCustomerName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                    FilterList = (List<GetCustomerData>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}


class cutomerItemHolder extends RecyclerView.ViewHolder {
    TextView name, phone, email;
    ImageView imgEmployee;
    LinearLayout parentLayout;

    cutomerItemHolder(@NonNull View itemView) {

        super(itemView);
        parentLayout = itemView.findViewById(R.id.parentLayout);
        name = itemView.findViewById(R.id.customerNameTextView);
        phone = itemView.findViewById(R.id.customerPhoneTextVIew);
        email = itemView.findViewById(R.id.customerEmailTextView);
        imgEmployee = itemView.findViewById(R.id.imgEmployee);

    }
}

class CustomerHeader extends RecyclerView.ViewHolder {
    public CustomerHeader(@NonNull View itemView) {
        super(itemView);
    }
}
