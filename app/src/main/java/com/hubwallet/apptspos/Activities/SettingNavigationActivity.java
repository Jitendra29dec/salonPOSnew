package com.hubwallet.apptspos.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hubwallet.apptspos.R;

public class SettingNavigationActivity extends AppCompatActivity {
    LinearLayout tabBusiness;
    public static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_navigation);
       // fm = getSupportFragmentManager();
        tabBusiness = findViewById(R.id.tabBusiness);
        tabBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  fm.beginTransaction().replace(R.id.containerMain, new SettingFragment())
                        .commit();*/
            }
        });
    }


}
