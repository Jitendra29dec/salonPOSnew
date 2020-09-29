package com.hubwallet.apptspos.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.hubwallet.apptspos.APis.GetServices;
import com.hubwallet.apptspos.APis.GetStylists;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesList;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData;
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistList;
import com.hubwallet.apptspos.Utils.Models.ServiceStylist;
import com.hubwallet.apptspos.Utils.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesStylistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ApiCommunicator {
    private Context context;
    private ArrayList<ServiceStylist> list;
    private int ADD_BUTTON = 1001;
    private int CONTENT = 1002;
    private int listSize;
    private ApiCommunicator apiCommunicator;
    private Spinner stylistSpinner, serviceSpinner;
    private List<GetStylistData> stylistList = null;
    private List<GetServicesData> servicesList = null;
    private String venderId;
    private TextView priceTextView, durationTextView;

    public ServicesStylistAdapter(Context context, ArrayList<ServiceStylist> list) {
        this.venderId = new PrefManager(context).getVendorId();
        this.context = context;
        this.list = list;
        this.apiCommunicator = this;
        if (list == null) {
            listSize = 0;
        } else {
            listSize = list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == listSize) {
            type = ADD_BUTTON;
        } else {
            type = CONTENT;
        }
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        if (i == ADD_BUTTON) {
            View v = LayoutInflater.from(context).inflate(R.layout.add_card_view, null, false);
            v.setLayoutParams(lp);
            viewHolder = new addButtonHolder(v);
        }
        if (i == CONTENT) {
            View v = LayoutInflater.from(context).inflate(R.layout.content_for_service_stylist, null, false);
            v.setLayoutParams(lp);
            viewHolder = new cccHolder(v);

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (getItemViewType(i) == ADD_BUTTON) {
            addButtonHolder holder = (addButtonHolder) viewHolder;
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button ok;
                    View view = LayoutInflater.from(context).inflate(R.layout.servic_stylist_alert_dialog, null, false);
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    serviceSpinner = view.findViewById(R.id.ServiceSpinner);
                    stylistSpinner = view.findViewById(R.id.stylistSpinner);
                    priceTextView = view.findViewById(R.id.priceTextView);
                    durationTextView = view.findViewById(R.id.serviceDurationTextView);
                    ok = view.findViewById(R.id.okBusttonAlert);
                    MySingleton.getInstance(context).addToRequestQue(new GetServices(apiCommunicator, new PrefManager(context).getVendorId()).getStringRequest());
                    MySingleton.getInstance(context).addToRequestQue(new GetStylists(apiCommunicator, venderId).getStringRequest());
                    alertDialog.setCancelable(false);
                    serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (servicesList != null) {
                                durationTextView.setText("$" + servicesList.get(i).getPrice());
                                priceTextView.setText(servicesList.get(i).getDuration());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ServiceStylist data = new ServiceStylist();
                            int servpos = serviceSpinner.getSelectedItemPosition();
                            int stylPos = stylistSpinner.getSelectedItemPosition();
                            data.setServiceId(servicesList.get(servpos).getServiceID());
                            data.setServiceName(servicesList.get(servpos).getServiceName());
                            data.setStylistId(stylistList.get(stylPos).getStylistId());
                            data.setStylistName(stylistList.get(stylPos).getStylistName());
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.add(data);
                            listSize = list.size();
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setView(view);
                    alertDialog.show();
                }
            });
        }
        if (getItemViewType(i) == CONTENT) {
            cccHolder holder = (cccHolder) viewHolder;
            holder.serviceNAme.setText(list.get(i).getServiceName() + "\n" + "\n" + list.get(i).getStylistName());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(i);
                    listSize = list.size();
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listSize + 1;
    }

    @Override
    public void getApiData(String status, String response) {
        if (status.equalsIgnoreCase("services_list")) {
            servicesList = new GsonBuilder().create().fromJson(response, GetServicesList.class).getResult();
            ArrayList<String> names = new ArrayList<>();
            for (GetServicesData data : servicesList) {
                names.add(data.getServiceName());
            }
            serviceSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, names));
            Log.e("getApiData: ", response);
        }
        if (status.equalsIgnoreCase("get_stylists")) {
            stylistList = new GsonBuilder().create().fromJson(response, GetStylistList.class).getResult();
            ArrayList<String> names = new ArrayList<>();
            for (GetStylistData data : stylistList) {
                names.add(data.getStylistName());
            }
            stylistSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, names));

        }
    }

    public String getServiceStylistDetail() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("service_id", list.get(i).getServiceId());
                jsonObject.put("service_name", list.get(i).getServiceName());
                jsonObject.put("stylist_id", list.get(i).getStylistId());
                jsonObject.put("stylist_name", list.get(i).getStylistName());

                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }
}

class addButtonHolder extends RecyclerView.ViewHolder {
    LinearLayout linearLayout;

    addButtonHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.mainla);
    }
}

class cccHolder extends RecyclerView.ViewHolder {
    TextView serviceNAme;
    ImageView delete;

    cccHolder(@NonNull View itemView) {
        super(itemView);
        serviceNAme = itemView.findViewById(R.id.serviceNameTextView);
        delete = itemView.findViewById(R.id.deleteBUtton);
    }
}