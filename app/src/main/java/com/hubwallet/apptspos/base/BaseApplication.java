package com.hubwallet.apptspos.base;

import android.app.Application;
import android.os.Build;
import android.os.RemoteException;
import android.widget.Toast;

import com.pax.market.android.app.sdk.BaseApiService;
import com.pax.market.android.app.sdk.StoreSdk;
import com.pax.poslink.CommSetting;
import com.pax.poslink.POSLinkAndroid;
import com.pax.poslink.PosLink;
import com.pax.poslink.poslink.POSLinkCreator;

public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();
    String APP_KEY = "870VW6PTEXHFA16ATP9T";
    String APP_SECRET = "59WV8RY412IK7LPL1BL129PQFC1Y85BNCCH4KFM8";
    String SN = Build.SERIAL;
    @Override
    public void onCreate() {
        super.onCreate();
 /*       POSLinkAndroid.init(getApplicationContext());
        PosLink poslink = POSLinkCreator.createPoslink(this);
        CommSetting commSetting = new CommSetting();
        if (Build.MODEL.startsWith("E")) {
            commSetting.setType(CommSetting.USB);
        } else if (Build.MODEL.startsWith("A9")){
            commSetting.setType(CommSetting.AIDL);
        } else {
            commSetting.setType(CommSetting.TCP);
        }
        commSetting.setTimeOut("60000");
        commSetting.setSerialPort("COM1");
        commSetting.setBaudRate("9600");
        commSetting.setDestIP("172.16.20.15");
        commSetting.setDestPort("10009");
        commSetting.setMacAddr("");
        commSetting.setEnableProxy(false);*/
       // initPaxStoreSdk();
    }

    private void initPaxStoreSdk() {
        //todo Init AppKey，AppSecret and SN, make sure the appKey and appSecret is corret.
        //todo make sure to replace with your own app's appKey and appSecret

        StoreSdk.getInstance().init(getApplicationContext(), APP_KEY, APP_SECRET, SN, new BaseApiService.Callback() {
            @Override
            public void initSuccess() {
                //TODO Do your business here
            }

            @Override
            public void initFailed(RemoteException e) {
                //TODO Do failed logic here
//                Toast.makeText(getApplicationContext(), "Cannot get API URL from PAXSTORE, Please install PAXSTORE first.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
