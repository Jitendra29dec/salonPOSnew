package com.hubwallet.apptspos.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hubwallet.apptspos.APis.DeleteService;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterGetServices extends RecyclerView.Adapter<servicesHolder> {
    private Context context;
    private List<GetServicesData> list;
    private ApiCommunicator apiCommunicator;

    public AdapterGetServices(Context context, List<GetServicesData> list, ApiCommunicator apiCommunicator) {
        this.context = context;
        this.list = list;
        this.apiCommunicator = apiCommunicator;
    }

    @NonNull
    @Override
    public servicesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_for_getservices, null);
        return new servicesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull servicesHolder servicesHolder, final int i) {
        final int pos = i;
        servicesHolder.name.setText(list.get(i).getServiceName() + " " + "$" + list.get(i).getPrice());
        servicesHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NavigationActivity.isInProgress) {
                    apiCommunicator.getApiData("service_id", list.get(pos).getServiceID());
                }
            }
        });
        servicesHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MySingleton.getInstance(context).addToRequestQue(new DeleteService(list.get(i).getServiceID(), new PrefManager(context).getVendorId(), apiCommunicator).getStringRequest());

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class servicesHolder extends RecyclerView.ViewHolder {
    TextView name;
    LinearLayout linearLayout;
    ImageButton imageButton;

    public servicesHolder(@NonNull View itemView) {
        super(itemView);
        imageButton = itemView.findViewById(R.id.deleteServiceButton);
        linearLayout = itemView.findViewById(R.id.linearLayoutMain);
        name = itemView.findViewById(R.id.getServiceTextView);
    }
}


