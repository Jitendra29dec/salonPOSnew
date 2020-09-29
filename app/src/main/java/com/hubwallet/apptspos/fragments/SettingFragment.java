package com.hubwallet.apptspos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.hubwallet.apptspos.Activities.NavigationActivity;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.ApiCommunicator;


public class SettingFragment extends Fragment implements ApiCommunicator, View.OnClickListener,
        PopupMenu.OnMenuItemClickListener {
    private LinearLayout tabServices, tabBusiness, tabEmp, tabThingsSell, tabBooking, tabLookAndFeel, tabCustomer;
    private TextView service,  emp, tvThingsSell, tvBooking,tvLookFeel,tvAdsOn,tvMarketing;
    public static FragmentManager fm;
    private int curretItem = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, null, false);
        intView(view);

        service.setTextColor(getResources().getColor(R.color.colorPrimary));
        ServicesListFragment nextFrag = new ServicesListFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMainSubMenu, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();

        //  Toast.makeText(getContext(), "ItemClicked1", Toast.LENGTH_LONG).show();
        return view;

    }

    private void intView(View view){
        tabServices = view.findViewById(R.id.tabServices);
        tabBusiness = view.findViewById(R.id.tabBusiness);
        tabEmp = view.findViewById(R.id.tabEmployee);
        tabThingsSell = view.findViewById(R.id.tabThingsSell);
        tvThingsSell = view.findViewById(R.id.tvThingsSell);
        tabLookAndFeel = view.findViewById(R.id.tabLookFeel);
        tabCustomer = view.findViewById(R.id.tabCustomer);
        tabBooking = view.findViewById(R.id.tabBooking);
        tvBooking = view.findViewById(R.id.idBooking);
        tvLookFeel = view.findViewById(R.id.tvLookFeel);
        tvAdsOn = view.findViewById(R.id.tvAdaOn);
        tvMarketing = view.findViewById(R.id.tvMarketing);
        service = view.findViewById(R.id.service);
        tabBooking = view.findViewById(R.id.tabBooking);
        emp = view.findViewById(R.id.employee);

        tabServices.setOnClickListener(this);
        tabBusiness.setOnClickListener(this);
        tabEmp.setOnClickListener(this);
        tabThingsSell.setOnClickListener(this);
        tabBooking.setOnClickListener(this);
        tabLookAndFeel.setOnClickListener(this);
        tabCustomer.setOnClickListener(this);

        tabServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showService(view);
                service.setTextColor(getResources().getColor(R.color.colorPrimary));
                emp.setTextColor(getResources().getColor(R.color.setting_colour));
                tvThingsSell.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBooking.setTextColor(getResources().getColor(R.color.setting_colour));
                tvLookFeel.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAdsOn.setTextColor(getResources().getColor(R.color.setting_colour));
                tvMarketing.setTextColor(getResources().getColor(R.color.setting_colour));
              /*  NavigationActivity.fm.beginTransaction().replace(R.id.containerMain, new ServicesListFragment())
                        .commit();*/
            }
        });
        tabBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvThingsSell.setTextColor(getResources().getColor(R.color.setting_colour));
                service.setTextColor(getResources().getColor(R.color.setting_colour));
                emp.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBooking.setTextColor(getResources().getColor(R.color.setting_colour));
                tvLookFeel.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAdsOn.setTextColor(getResources().getColor(R.color.setting_colour));
                tvMarketing.setTextColor(getResources().getColor(R.color.setting_colour));

                //  Toast.makeText(getContext(), "ItemClicked1", Toast.LENGTH_LONG).show();
                showBusiness(view);
            }
        });

        tabEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emp.setTextColor(getResources().getColor(R.color.colorPrimary));
                service.setTextColor(getResources().getColor(R.color.setting_colour));
                tvThingsSell.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBooking.setTextColor(getResources().getColor(R.color.setting_colour));
                tvLookFeel.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAdsOn.setTextColor(getResources().getColor(R.color.setting_colour));
                tvMarketing.setTextColor(getResources().getColor(R.color.setting_colour));
                //  Toast.makeText(getContext(), "ItemClicked2", Toast.LENGTH_LONG).show();
                showEmployee(view);
            }
        });

        tabThingsSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvThingsSell.setTextColor(getResources().getColor(R.color.colorPrimary));
                service.setTextColor(getResources().getColor(R.color.setting_colour));
                emp.setTextColor(getResources().getColor(R.color.setting_colour));
                tvBooking.setTextColor(getResources().getColor(R.color.setting_colour));
                tvLookFeel.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAdsOn.setTextColor(getResources().getColor(R.color.setting_colour));
                tvMarketing.setTextColor(getResources().getColor(R.color.setting_colour));
                showThinkWeSell(view);
            }
        });

        tabBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBooking.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvThingsSell.setTextColor(getResources().getColor(R.color.setting_colour));
                service.setTextColor(getResources().getColor(R.color.setting_colour));
                emp.setTextColor(getResources().getColor(R.color.setting_colour));
                tvLookFeel.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAdsOn.setTextColor(getResources().getColor(R.color.setting_colour));
                tvMarketing.setTextColor(getResources().getColor(R.color.setting_colour));
                showBooking(view);
            }
        });

        tabLookAndFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBooking.setTextColor(getResources().getColor(R.color.setting_colour));
                tvThingsSell.setTextColor(getResources().getColor(R.color.setting_colour));
                service.setTextColor(getResources().getColor(R.color.setting_colour));
                emp.setTextColor(getResources().getColor(R.color.setting_colour));
                tvLookFeel.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvAdsOn.setTextColor(getResources().getColor(R.color.setting_colour));
                tvMarketing.setTextColor(getResources().getColor(R.color.setting_colour));
                showLookAndFeel(view);
            }
        });


        tabCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBooking.setTextColor(getResources().getColor(R.color.setting_colour));
                tvThingsSell.setTextColor(getResources().getColor(R.color.setting_colour));
                service.setTextColor(getResources().getColor(R.color.setting_colour));
                emp.setTextColor(getResources().getColor(R.color.setting_colour));
                tvLookFeel.setTextColor(getResources().getColor(R.color.setting_colour));
                tvAdsOn.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMarketing.setTextColor(getResources().getColor(R.color.setting_colour));
                showCustomer(view);
            }
        });
    }

    @Override
    public void getApiData(String status, String response) {
        ((NavigationActivity) getActivity()).offProgress();
    }

    private void showBusiness(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.business_menu);
        popupMenu.show();

    }

    private void showEmployee(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.employee_menu);
        popupMenu.show();

    }

    private void showBooking(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.booking_menu);
        popupMenu.show();

    }


    private void showThinkWeSell(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.think_we_sell_menu);
        popupMenu.show();

    }

    private void showLookAndFeel(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.lookandfeel_menu);
        popupMenu.show();

    }

    private void showCustomer(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.customer_menu);
        popupMenu.show();

    }

    private void showService(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.service_menu);
        popupMenu.show();

    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.accessLevel:
                ((NavigationActivity) getActivity()).onProgress();
                AccessLevelFragment accessLevelFragment = new AccessLevelFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, accessLevelFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.permission:
                ((NavigationActivity) getActivity()).onProgress();
                PermissionFragment permissionFragment = new PermissionFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, permissionFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.serviceCategory:
                ((NavigationActivity) getActivity()).onProgress();
                ListServiceCategory listServiceCategory = new ListServiceCategory();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, listServiceCategory, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return  true;
            case R.id.service:
                ServicesListFragment nextFrag = new ServicesListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.calanderConfig:
                CalanderConfigFragment calanderConfigFragment = new CalanderConfigFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, calanderConfigFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.calendarColour:
                CalendarColourFragment calendarColourFragment = new CalendarColourFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, calendarColourFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;

            case R.id.photoGallery:
                ((NavigationActivity) getActivity()).onProgress();
                PhotoGalleryFragment galleryFragment = new PhotoGalleryFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, galleryFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.colourTheme:
                ColourThemeFragment colourThemeFragment = new ColourThemeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, colourThemeFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return  true;

            case R.id.discount:
                ((NavigationActivity) getActivity()).onProgress();
                DiscountFragment discountFragment = new DiscountFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, discountFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.coupon:
                ((NavigationActivity) getActivity()).onProgress();
                CouponFragment couponFragment = new CouponFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, couponFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.giftCertificate:
                GiftCertificateFragment giftCertificateFragment = new GiftCertificateFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, giftCertificateFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.iou:
                IouFragment iouFragment = new IouFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, iouFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.emailNotification:
                EmailConfigFragment emailConfigFragment = new EmailConfigFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, emailConfigFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.taxes:
               // ((NavigationActivity) getActivity()).onProgress();
                TaxesFragment taxesFragment = new TaxesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, taxesFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.tvScreen:
                TvAppSetting tvAppSetting = new TvAppSetting();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, tvAppSetting, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.screenLock:
                ScreenLockTimeFragment screenLockTimeFragment = new ScreenLockTimeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerMainSubMenu, screenLockTimeFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabBusiness:
                break;
            case R.id.tabEmployee:
                break;
            case R.id.tabServices:
                break;
            case R.id.tabBooking:
                break;
            case R.id.tabThingsSell:
                break;
            case R.id.tabLookFeel:
                break;
            case R.id.tabCustomer:
                break;
        }
    }
}
