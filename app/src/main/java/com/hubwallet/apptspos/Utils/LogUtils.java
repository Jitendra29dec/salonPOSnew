package com.hubwallet.apptspos.Utils;

import android.util.Log;

public class LogUtils {

    private static final boolean isShowLog = true;

    public static void printLog(String tag, String msg) {
        if (isShowLog)
            Log.e(tag, msg);
    }

    public static void showToast(String msg) {
//        Toast.makeText()
    }

}
