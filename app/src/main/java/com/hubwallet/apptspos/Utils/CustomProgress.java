package com.hubwallet.apptspos.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class CustomProgress extends Dialog {
    public CustomProgress(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ProgressBar progressBar = new ProgressBar(context);
        setContentView(progressBar);
        setCancelable(false);
    }
}
