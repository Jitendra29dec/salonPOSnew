package com.hubwallet.apptspos.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.hubwallet.apptspos.APis.LogOutApi;
import com.hubwallet.apptspos.APis.MySingleton;
import com.hubwallet.apptspos.base.BaseActivity;
import com.hubwallet.apptspos.checkout.CheckoutFragment;
import com.hubwallet.apptspos.fragments.AddBrandFragment;
import com.hubwallet.apptspos.fragments.AddCategoryFragment;
import com.hubwallet.apptspos.fragments.AddCouponFragment;
import com.hubwallet.apptspos.fragments.AddCustomerFragment;
import com.hubwallet.apptspos.fragments.AddNewDiscount;
import com.hubwallet.apptspos.fragments.AddNewServiceCategory;
import com.hubwallet.apptspos.fragments.AddNewServices;
import com.hubwallet.apptspos.fragments.AddPurchaseOrderFragment;
import com.hubwallet.apptspos.fragments.AddVendorFragment;
import com.hubwallet.apptspos.fragments.BrandListFragment;
import com.hubwallet.apptspos.fragments.CalenderFragment;
import com.hubwallet.apptspos.fragments.CategoryListFragment;
import com.hubwallet.apptspos.fragments.CheckoutRightSide;
import com.hubwallet.apptspos.fragments.CouponFragment;
import com.hubwallet.apptspos.fragments.CustomerLIstFragment;
import com.hubwallet.apptspos.fragments.DiscountFragment;
import com.hubwallet.apptspos.fragments.EmployeeMenuFragment;
import com.hubwallet.apptspos.fragments.ListServiceCategory;
import com.hubwallet.apptspos.fragments.ListingFragmentForProductAdd;
import com.hubwallet.apptspos.fragments.ListingFragmentForProductList;
import com.hubwallet.apptspos.fragments.OrderDetailFragment;
import com.hubwallet.apptspos.fragments.OrderFragment;
import com.hubwallet.apptspos.fragments.PaymentFragment;
import com.hubwallet.apptspos.fragments.ProductMenuFragment;
import com.hubwallet.apptspos.fragments.PurchaseOrderList;
import com.hubwallet.apptspos.fragments.SalesRightSide;
import com.hubwallet.apptspos.fragments.ServiceList;
import com.hubwallet.apptspos.fragments.ServicesFragmet;
import com.hubwallet.apptspos.fragments.ServicesListFragment;
import com.hubwallet.apptspos.fragments.SettingFragment;
import com.hubwallet.apptspos.fragments.StylishFragment;
import com.hubwallet.apptspos.fragments.StylishTimingFragment;
import com.hubwallet.apptspos.fragments.SwipeFragment;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;
import com.hubwallet.apptspos.Utils.Communicator;
import com.hubwallet.apptspos.Utils.LogUtils;
import com.hubwallet.apptspos.Utils.PrefManager;
import com.hubwallet.apptspos.fragments.VendorListFragment;
import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class NavigationActivity extends BaseActivity
        implements Communicator, ApiCommunicator {

    public static boolean enableMonthToggle = false;
    public static boolean isInProgress = false;
    public static FragmentManager fm;

    FrameLayout l1, l2;
    int currentMonth;
    boolean ison = false;
    ProgressDialog progressDialog;
    CardView calendar, services, listing, customer, sales, employee, previous, next, order, clockIn, setting;
    TextView tab, month;
    ApiCommunicator apiCommunicator;
    int[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    CheckoutRightSide checkoutRightSide;
    CheckoutFragment salesSwipeFragment;
    CalenderFragment calenderFragment;
    ProgressBar progressBar;
    private TextView time;
    private SwipeFragment swipeFragment;
    private PaymentFragment paymentFragment;
    //    private StarBluetoothManager mBluetoothManager;
    private StarIOPort port;
    public int curretItem = 1;

    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Logout before exiting?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                MySingleton.getInstance(NavigationActivity.this).addToRequestQue(new LogOutApi(new PrefManager(NavigationActivity.this).getLogiId(), apiCommunicator).getStringRequest());
                progressDialog.show();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_navigation);
        initialize();
        final ArrayList<String> xVals = new ArrayList<>();
        final Calendar cale = Calendar.getInstance();
        currentMonth = cale.get(Calendar.MONTH);
        final Handler someHandler = new Handler(getMainLooper());

        fm = getSupportFragmentManager();

        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time.setText(new SimpleDateFormat("MM-dd-yyyy , HH:mm:ss", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);
        for (int month1 : months) {
            Calendar cal = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, month1);
            String month_name = month_date.format(cal.getTime());
            xVals.add(month_name);

        }
        //order.setVisibility(View.GONE);
        order.setOnClickListener(v -> {
            curretItem = 7;
            onProgress();
            tab.setText("Orders");
            l2.setVisibility(View.GONE);
            fm.beginTransaction().replace(R.id.containerMain, new OrderFragment())
                    .commit();

        });

        setting.setOnClickListener(view -> {
            curretItem = 9;
            onProgress();
            tab.setText("Setting");
            l2.setVisibility(View.GONE);
        //    startActivity(new Intent(NavigationActivity.this,SettingNavigationActivity.class));
            fm.beginTransaction().replace(R.id.containerMain, new SettingFragment())
                    .commit();
        });

        employee.setOnClickListener(v -> {
            if (curretItem != 6) {
                curretItem = 6;
                onProgress();
                l2.setVisibility(View.GONE);
                //    fm.beginTransaction().replace(R.id.container2, new StylishTimingFragment()).commit();
                fm.beginTransaction().replace(R.id.containerMain, new EmployeeMenuFragment())
                        .commit();
                isInProgress = false;
            }
        });
        sales.setOnClickListener(v -> {
            if (curretItem != 5) {
                curretItem = 5;
//                    onProgress();
                salesSwipeFragment =  CheckoutFragment.Companion.newInstance();
                tab.setText("Sales");
                l2.setVisibility(View.GONE);
                fm.beginTransaction().replace(R.id.containerMain, salesSwipeFragment)
                        .commit();

            }
        });
        l2.setVisibility(View.GONE);
        listing.setOnClickListener(v -> {
            if (curretItem != 4) {
                curretItem = 4;
                onProgress();
                tab.setText("Listing");
                l2.setVisibility(View.GONE);
//                    fm.beginTransaction().replace(R.id.container2, new ListingFragmentForProductAdd())
//                            .commit();
                fm.beginTransaction().replace(R.id.containerMain, new ProductMenuFragment())
                        .commit();
            }
        });
        services.setOnClickListener(v -> {
            if (curretItem != 3) {
                curretItem = 3;
                onProgress();
                l2.setVisibility(View.VISIBLE);
                fm.beginTransaction().replace(R.id.containerMain, new ServiceList())
                        .commit();
                isInProgress = false;
                fm.beginTransaction().replace(R.id.container2, new ServicesFragmet())
                        .commit();
                tab.setText("Services");
            }
        });
        //  l2.setVisibility(View.VISIBLE);
        fm.beginTransaction().replace(R.id.containerMain, calenderFragment)
                .commit();

        customer.setOnClickListener(v -> {
            if (curretItem != 2) {
                curretItem = 2;
                onProgress();
                l2.setVisibility(View.GONE);
                fm.beginTransaction().replace(R.id.containerMain, new CustomerLIstFragment())
                        .commit();
                //fm.beginTransaction().replace(R.id.container2, new AddCustomerFragment()).commit();
                // l2.setVisibility(View.VISIBLE);
                isInProgress = false;
                tab.setText("Customers");
            }
        });
        calendar.setOnClickListener(v -> {
            if (curretItem != 1) {
//                    onProgress();
                curretItem = 1;
                l2.setVisibility(View.GONE);
                fm.beginTransaction().replace(R.id.containerMain, calenderFragment)
                        .commit();
                tab.setText("Appointment");
            }
        });
        clockIn.setOnClickListener(v -> startActivity(new Intent(NavigationActivity.this, PinCodeActivity.class)));
        month.setText(xVals.get(currentMonth) + " " + Calendar.getInstance().get(Calendar.YEAR));

        next.setOnClickListener(v -> {
//                if (enableMonthToggle) {
//                    if (currentMonth < 11) {
//                        currentMonth++;
//                    }
//                    month.setText(xVals.get(currentMonth) + " " + Calendar.getInstance().get(Calendar.YEAR));
//                    calenderFragment.changeMonth(currentMonth);
//                }
            calenderFragment.changeMonth(1);

        });

        previous.setOnClickListener(v -> {
//                if (enableMonthToggle) {
//                    if (currentMonth > 0) {
//                        currentMonth--;
//                    }
//                    month.setText(xVals.get(currentMonth) + " " + Calendar.getInstance().get(Calendar.YEAR));
//                    calenderFragment.changeMonth(currentMonth);
//                }
            calenderFragment.changeMonth(6);

        });

        screenLockTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<PortInfo> portList = StarIOPort.searchPrinter("BT:");
            for (PortInfo portInfo : portList) {
                port = StarIOPort.getPort(portInfo.getPortName(), "", 10000, this);
            }
        } catch (StarIOPortException e) {
            e.printStackTrace();
            LogUtils.printLog("OnResume Exc", "::  " + e.getMessage());
        }
    }


    private void initialize() {
        l1 = findViewById(R.id.containerMain);
        month = findViewById(R.id.month);
        employee = findViewById(R.id.stylishView);
        setting = findViewById(R.id.setting);
        l2 = findViewById(R.id.container2);
        tab = findViewById(R.id.tabBar);
        time = findViewById(R.id.timeBar);
        previous = findViewById(R.id.previous);
        customer = findViewById(R.id.customerView);
        listing = findViewById(R.id.listing);
        services = findViewById(R.id.servicesView);
        calendar = findViewById(R.id.calendarView);
        sales = findViewById(R.id.salesView);
        next = findViewById(R.id.next);
        order = findViewById(R.id.orderView);
        clockIn = findViewById(R.id.clockInView);
        apiCommunicator = this;
        progressBar = findViewById(R.id.MainProgressBar);
        progressDialog = new ProgressDialog(this);
        calenderFragment = new CalenderFragment();
        progressDialog.setMessage("Please wait.");
        checkoutRightSide = new CheckoutRightSide();
        onProgress();
    }


    @Override
    public void sendmessage(String message, String response) {
        if (message.equalsIgnoreCase("payment_frag")) {
            paymentFragment = new PaymentFragment();
            fm.beginTransaction().replace(R.id.containerMain, paymentFragment).commit();
            tab.setText("Payment");

        }
        if (message.equalsIgnoreCase("check_out_swipe_data")) {
            swipeFragment.updatePage(Integer.parseInt(response));
        }
        if (message.equalsIgnoreCase("sales_swipe_data")) {
//            salesSwipeFragment.updatePage(Integer.parseInt(response));
        }
        if (message.equalsIgnoreCase("add")) {
            if (!ison) {
//                fm.beginTransaction().replace(R.id.container2, new AddCustomerFragment()).commit();
                DialogFragment df = new AddCustomerFragment();
                df.show(getSupportFragmentManager(), "Add Customer");
                l2.setVisibility(View.VISIBLE);
                ison = true;
            }
        } else {
            if (ison) {
                //   l2.setVisibility(View.GONE);
                ison = false;
            }
        }
        if (message.equalsIgnoreCase("1")) {
            fm.beginTransaction().replace(R.id.containerMain, new SalesRightSide()).commit();

        }

        if (message.equalsIgnoreCase("checkout")) {
            tab.setText("Checkout");
            Bundle bundle = new Bundle();
            bundle.putString("appointment_id", response);
            swipeFragment = new SwipeFragment();
            swipeFragment.setArguments(bundle);
            curretItem = -1;
            fm.beginTransaction().replace(R.id.containerMain, swipeFragment).commit();

        }
        if (message.equalsIgnoreCase("sales")) {
            tab.setText("Sales");
//            salesSwipeFragment.updateForSale(response);
        }
        if (message.equalsIgnoreCase("customers")) {
            tab.setText("Customers");
            fm.beginTransaction().replace(R.id.containerMain, new CustomerLIstFragment()).commit();
//            fm.beginTransaction().replace(R.id.container2, new AddCustomerFragment()).commit();
            l2.setVisibility(View.VISIBLE);
        }
        if (message.equalsIgnoreCase("stylist")) {
            tab.setText("Stylist");
            if (response.isEmpty()) {
                fm.beginTransaction().replace(R.id.container2, new StylishTimingFragment()).commit();
                fm.beginTransaction().replace(R.id.containerMain, new StylishFragment()).commit();
            } else if (response.equalsIgnoreCase("delete")) {
                fm.beginTransaction().replace(R.id.containerMain, new StylishFragment()).commit();
            } else {
                StylishTimingFragment fragment = new StylishTimingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("stylist_id", response);
                fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.container2, fragment).commit();

            }
        }
        if (message.equalsIgnoreCase("customer_edit")) {
            if (response.equalsIgnoreCase("")) {
//                fm.beginTransaction().replace(R.id.container2, new AddCustomerFragment()).commit();
                DialogFragment df = new AddCustomerFragment();
                df.show(getSupportFragmentManager(), "Add Customer");
            } else if (response.equalsIgnoreCase("delete")) {
//                fm.beginTransaction().replace(R.id.containerMain, new CustomerLIstFragment()).commit();
                DialogFragment df = new AddCustomerFragment();
                df.show(getSupportFragmentManager(), "Add Customer");
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("info", response);
                AddCustomerFragment addCustomerFragment = new AddCustomerFragment();
                addCustomerFragment.setArguments(bundle);
//                fm.beginTransaction().replace(R.id.container2, addCustomerFragment).commit();
//                DialogFragment df = new AddCustomerFragment();
                addCustomerFragment.show(getSupportFragmentManager(), "Add Customer");

            }
        }

        if (message.equalsIgnoreCase("listing")) {
            tab.setText("Listing");
            if (response.equalsIgnoreCase("")) {
                l2.setVisibility(View.GONE);
//                fm.beginTransaction().replace(R.id.container2, new ListingFragmentForProductAdd()).commit();
                fm.beginTransaction().replace(R.id.productMainSubMenu, new ListingFragmentForProductList()).commit();
            } else if (response.equalsIgnoreCase("delete")) {
                fm.beginTransaction().replace(R.id.containerMain, new ListingFragmentForProductList()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("product_id", response);
                ListingFragmentForProductAdd fragmentForProductAdd = new ListingFragmentForProductAdd();
                fragmentForProductAdd.setArguments(bundle);
                fm.beginTransaction().replace(R.id.productMainSubMenu, fragmentForProductAdd).commit();

            }
        }
        if (message.equalsIgnoreCase("services")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.containerMainSubMenu, new ServicesListFragment()).commit();
             //   fm.beginTransaction().replace(R.id.container2, new ServicesFragmet()).commit();
            } else if (response.equalsIgnoreCase("delete")) {
                fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("service_id", response);
                //  fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
               // ServicesFragmet servicesFragmet = new ServicesFragmet();
                AddNewServices addNewServices = new AddNewServices();
                addNewServices.setArguments(bundle);
                fm.beginTransaction().replace(R.id.containerMainSubMenu, addNewServices).commit();
            }
        }
        if (message.equalsIgnoreCase("category_added")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.productMainSubMenu, new CategoryListFragment()).commit();
                //   fm.beginTransaction().replace(R.id.container2, new ServicesFragmet()).commit();
            } else if (response.equalsIgnoreCase("delete")) {
                fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("category_id", response);
                //  fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
                // ServicesFragmet servicesFragmet = new ServicesFragmet();
                AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
                addCategoryFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.productMainSubMenu, addCategoryFragment).commit();
            }
        }

        if (message.equalsIgnoreCase("vendor_add")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.productMainSubMenu, new VendorListFragment()).commit();
                //   fm.beginTransaction().replace(R.id.container2, new ServicesFragmet()).commit();
            } else if (response.equalsIgnoreCase("delete")) {
                fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("supplier_id", response);
                //  fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
                // ServicesFragmet servicesFragmet = new ServicesFragmet();
                AddVendorFragment addVendorFragment = new AddVendorFragment();
                addVendorFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.productMainSubMenu, addVendorFragment).commit();
            }
        }

        if (message.equalsIgnoreCase("po_added")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.productMainSubMenu, new PurchaseOrderList()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("po_id", response);
                AddPurchaseOrderFragment addPurchaseOrderFragment = new AddPurchaseOrderFragment();
                addPurchaseOrderFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.containerMainSubMenu, addPurchaseOrderFragment).commit();
            }
        }

        if (message.equalsIgnoreCase("coupon_added")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.containerMainSubMenu, new CouponFragment()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("coupon_id", response);
                AddCouponFragment addCouponFragment = new AddCouponFragment();
                addCouponFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.containerMainSubMenu, addCouponFragment).commit();
            }
        }
        if (message.equalsIgnoreCase("discount")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.containerMainSubMenu, new DiscountFragment()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("discount_id", response);
                AddNewDiscount addNewDiscount = new AddNewDiscount();
                addNewDiscount.setArguments(bundle);
                fm.beginTransaction().replace(R.id.containerMainSubMenu, addNewDiscount).commit();
            }
        }

        if (message.equalsIgnoreCase("serviceCat_added")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.containerMainSubMenu, new ListServiceCategory()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("category_id", response);
                AddNewServiceCategory addNewServiceCategory = new AddNewServiceCategory();
                addNewServiceCategory.setArguments(bundle);
                fm.beginTransaction().replace(R.id.containerMainSubMenu, addNewServiceCategory).commit();
            }
        }
        if (message.equalsIgnoreCase("brand_added")) {
            if (response.equalsIgnoreCase("")) {
                fm.beginTransaction().replace(R.id.productMainSubMenu, new BrandListFragment()).commit();
                //   fm.beginTransaction().replace(R.id.container2, new ServicesFragmet()).commit();
            } else if (response.equalsIgnoreCase("delete")) {
                fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("brand_id", response);
                //  fm.beginTransaction().replace(R.id.containerMain, new ServiceList()).commit();
                // ServicesFragmet servicesFragmet = new ServicesFragmet();
                AddBrandFragment addBrandFragment = new AddBrandFragment();
                addBrandFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.productMainSubMenu, addBrandFragment).commit();
            }
        }

        if (message.equalsIgnoreCase("order_detail")) {
            Bundle bundle = new Bundle();
            bundle.putString("order_id", response);
            OrderDetailFragment detailFragment = new OrderDetailFragment();
            detailFragment.setArguments(bundle);
            l2.setVisibility(View.GONE);
            fm.beginTransaction().replace(R.id.containerMain, detailFragment).commit();
        }
    }

    @Override
    public void getApiData(String status, String response) {
        progressDialog.dismiss();
        if (status.equalsIgnoreCase("1")) {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            new PrefManager(this).clearSession();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        }
    }

    public void onProgress() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void offProgress() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    public void PrintPriceFunction(View view) {
        paymentFragment.PriceHandel(view);
    }

    public void QuickAdd(View view) {
    }
}
