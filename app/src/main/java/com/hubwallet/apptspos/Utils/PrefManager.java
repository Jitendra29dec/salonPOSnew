package com.hubwallet.apptspos.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hubwallet.apptspos.Localdatabase.Servicedatasa;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PrefManager {
    private SharedPreferences preferences;
    private String logiId = "LOGIN_ID";
    private String vendorId = "VENDOR_ID";
    private String vendorName = "VENDOR_NAME";
    private String data = "SERVICES_DATA";
    public static String ACTIVITY_SCREEN_lOCK = "ACTIVITY_SCREEN_lOCK";
    public static String LOCK_TIME = "LOCK_TIME";
    public static String WARNING_TIME = "WARNING_TIME";
    public static final String FCMTOKEN = "FCMTOKEN";
    private static SharedPreferences.Editor editor;
    public PrefManager(Context context) {
        String sharedPreferenceName = "POS_SHARED_PREFERENCE";
        preferences = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public String getVendorName() {
        return preferences.getString(vendorName, "");
    }

    public void setVendorName(String name) {
        preferences.edit().putString(vendorName, name).apply();

    }

    public String getVendorId() {
        return preferences.getString(vendorId, "1");
    }

    public void setVendorId(String vid) {
        preferences.edit().putString(vendorId, vid).apply();

    }

    public String getLogiId() {
        return preferences.getString(logiId, "");

    }

    public void setLogiId(String val) {
        preferences.edit().putString(logiId, val).apply();

    }

    public void clearSession() {
        preferences.edit().putString(logiId, "").apply();
        preferences.edit().putString(vendorName, "").apply();
        preferences.edit().putString(vendorId, "").apply();
    }

    public void saveInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public String getData() {
        return preferences.getString(data, "");

    }

    public int getIntValue(String key) {
        return preferences.getInt(key, 0);

    }

    public void setData(String val) {

        preferences.edit().putString(data, val).apply();

    }

    public void setData(String key, String val) {
        preferences.edit().putString(key, val).apply();
    }

    public String getData(String key) {
        return preferences.getString(key, "");
    }


    public  void setList(String key, List<Servicedatasa> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json);
    }

    public static void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }


    public List<Servicedatasa> getList(String key) {
        List<Servicedatasa> arrayItems =new ArrayList<>();
        String serializedObject = preferences.getString(key, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Servicedatasa>>() {
            }.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }
}

