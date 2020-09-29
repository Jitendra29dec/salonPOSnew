package com.hubwallet.apptspos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.Models.CheckoutDetails.Cart;
import com.hubwallet.apptspos.Utils.Models.CheckoutDetails.CheckoutData;
import com.hubwallet.apptspos.Utils.Models.CheckoutDetails.Servicess;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CheckoutDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private CheckoutData data;
    private List<Cart> carts;
    private List<Servicess> services;
    private int HEADER = 101;
    private int SERVICES_DETAIL = 102;
    private int PRODUCT_DETAIL = 103;
    private int PRODUCT_HEADER = 104;
    private List<String> productDiscount = null;
    private List<String> serviceDiscount = null;


    public CheckoutDetailsAdapter(Context context, CheckoutData data) {
        this.context = context;
        this.data = data;
        this.carts = data.getCart();
        this.services = data.getService();

    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == 0) {
            type = HEADER;
        }
        if (position <= services.size() && position != 0) {
            type = SERVICES_DETAIL;
        }
        if (position == services.size() + 1) {
            type = PRODUCT_HEADER;
        }
        if (position > services.size() + 1) {
            type = PRODUCT_DETAIL;

        }
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView.ViewHolder holder = null;

        if (i == HEADER) {

            View serviceHeader = LayoutInflater.from(context).inflate(R.layout.service_heading_checkout, null, false);
            serviceHeader.setLayoutParams(lp);
            holder = new Header(serviceHeader);

        }
        if (i == SERVICES_DETAIL) {
            View service = LayoutInflater.from(context).inflate(R.layout.services_row, null, false);
            service.setLayoutParams(lp);
            holder = new ServicesDetail(service);

        }
        if (i == PRODUCT_HEADER) {
            View serviceHeader = LayoutInflater.from(context).inflate(R.layout.product_header, null, false);
            serviceHeader.setLayoutParams(lp);
            holder = new ProductHeader(serviceHeader);
        }
        if (i == PRODUCT_DETAIL) {
            View productDetail = LayoutInflater.from(context).inflate(R.layout.product_row, null, false);
            productDetail.setLayoutParams(lp);
            holder = new CartDeatils(productDetail);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == HEADER) {
            Header header = (Header) viewHolder;
            if (services.size() != 0) {
                header.header.setText("Services");
            } else {
                header.mainHeader.setVisibility(View.GONE);
                header.header.setVisibility(View.GONE);

            }

        }
        if (getItemViewType(i) == PRODUCT_HEADER) {
            ProductHeader productHeader = (ProductHeader) viewHolder;
            if (carts.size() != 0) {
            } else {
                productHeader.linearLayout.setVisibility(View.GONE);
            }
        }
        if (getItemViewType(i) == SERVICES_DETAIL) {

            final ServicesDetail servicesDetail = (ServicesDetail) viewHolder;
            final Servicess obj = services.get(i - 1);
            servicesDetail.serviceName.setText(obj.getServiceName());
            if (obj.getStylistPrice().equalsIgnoreCase("0")) {
                servicesDetail.ServicePrice.setText("$" + obj.getServicePrice());
                servicesDetail.total.setText("$" + obj.getServicePrice());
            } else {
                servicesDetail.ServicePrice.setText("$" + obj.getStylistPrice());
                servicesDetail.total.setText("$" + obj.getStylistPrice());
            }
            if (obj.getStylistDuration().equalsIgnoreCase("0")) {
                servicesDetail.duration.setText(obj.getServiceDuration());
            } else {
                servicesDetail.duration.setText(obj.getStylistDuration());
            }
            servicesDetail.stylist.setText(obj.getStylistName());
            servicesDetail.due.setText("0");
            if (serviceDiscount != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.textveiw, serviceDiscount);
                servicesDetail.spinner.setAdapter(adapter);
                servicesDetail.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            Float total = obj.getStylistPrice().equalsIgnoreCase("0") ? Float.parseFloat(obj.getServicePrice()) : Float.parseFloat(obj.getStylistPrice());
                            servicesDetail.total.setText("$" + total + "");
                        } else {
                            String[] discount = serviceDiscount.get(i).split("%");
                            Float di = Float.parseFloat(discount[0]);
                            Float total = obj.getStylistPrice().equalsIgnoreCase("0") ? Float.parseFloat(obj.getServicePrice()) : Float.parseFloat(obj.getStylistPrice());
                            Float dAmount = total * (di / 100);
                            total -= dAmount;
                            servicesDetail.total.setText("$" + total + "");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
        if (getItemViewType(i) == PRODUCT_DETAIL) {
            final CartDeatils cartDeatils = (CartDeatils) viewHolder;
            final Cart ob = carts.get(i - services.size() - 2);
            cartDeatils.barcode.setText(ob.getBarcodeId());
            cartDeatils.due.setText("0");
            cartDeatils.price.setText("$" + ob.getPriceRetail());
            cartDeatils.productQuantity.setText("X" + ob.getQuantity());
            cartDeatils.productName.setText(ob.getProductName());
            cartDeatils.totalPrice.setText("$" + (Float.parseFloat(ob.getPriceRetail()) * Float.parseFloat(ob.getQuantity())));
            if (productDiscount != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.textveiw, productDiscount);
                cartDeatils.spinner.setAdapter(adapter);
                cartDeatils.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            Float total = Float.parseFloat(ob.getPriceRetail());
                            cartDeatils.totalPrice.setText("$" + total + "");
                        } else {
                            String[] discount = serviceDiscount.get(i).split("%");
                            Float di = Float.parseFloat(discount[0]);
                            Float total = Float.parseFloat(ob.getPriceRetail());
                            Float dAmount = total * (di / 100);
                            total -= dAmount;
                            cartDeatils.totalPrice.setText("$" + total + "");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return services.size() + carts.size() + 2;//+ for both header


    }

    public void updateServiceDiscount(List<String> data) {
        serviceDiscount = data;
        notifyDataSetChanged();
    }

    public void updateProductDiscount(List<String> data) {
        productDiscount = data;
        notifyDataSetChanged();
    }
}

class Header extends RecyclerView.ViewHolder {
    TextView header;
    LinearLayout mainHeader;

    Header(@NonNull View itemView) {
        super(itemView);
        mainHeader = itemView.findViewById(R.id.mainHeader);
        header = itemView.findViewById(R.id.serviceHeading);
    }
}

class ProductHeader extends RecyclerView.ViewHolder {
    LinearLayout linearLayout;

    ProductHeader(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.productHeader);
    }
}

class ServicesDetail extends RecyclerView.ViewHolder {
    TextView serviceName, ServicePrice, stylist, total, due, extraService, duration;
    Spinner spinner;

    ServicesDetail(@NonNull View itemView) {
        super(itemView);
        spinner = itemView.findViewById(R.id.serviceRowSpinner);
        duration = itemView.findViewById(R.id.durationRowServices);
        serviceName = itemView.findViewById(R.id.serviceName);
        ServicePrice = itemView.findViewById(R.id.servicePrice);
        stylist = itemView.findViewById(R.id.stylistRow);
        total = itemView.findViewById(R.id.totalRowServices);
        due = itemView.findViewById(R.id.dueRowServices);
        extraService = itemView.findViewById(R.id.extraServiceRow);
    }

}

class CartDeatils extends RecyclerView.ViewHolder {
    TextView productName, productQuantity, totalPrice, barcode, due, price;
    Spinner spinner;

    CartDeatils(@NonNull View itemView) {
        super(itemView);
        price = itemView.findViewById(R.id.priceProduct);
        due = itemView.findViewById(R.id.dueProduct);
        barcode = itemView.findViewById(R.id.barcodeProduct);
        productName = itemView.findViewById(R.id.productName);
        productQuantity = itemView.findViewById(R.id.productQuantity);
        totalPrice = itemView.findViewById(R.id.totalProductPrice);
        spinner = itemView.findViewById(R.id.productRowSpinner);
    }
}