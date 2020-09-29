package com.hubwallet.apptspos.APis;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mySingleTon;
    private RequestQueue requestQueue;
    private Context mctx;

    private MySingleton(Context context) {
        mctx = context;
        this.requestQueue = getRequestQueue();

    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mySingleTon == null) {
            mySingleTon = new MySingleton(context);
        }
        return mySingleTon;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQue(Request<T> request) {
        requestQueue.add(request);

    }
}
