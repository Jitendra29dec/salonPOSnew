package com.hubwallet.apptspos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.PrefManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        PrefManager manager = new PrefManager(this);
        manager.saveInt(PrefManager.ACTIVITY_SCREEN_lOCK, 0);
        manager.saveInt(PrefManager.LOCK_TIME, 0);
        manager.saveInt(PrefManager.WARNING_TIME, 0);
        final PrefManager prefManager = new PrefManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefManager.getLogiId().equalsIgnoreCase("")) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, NavigationActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}
