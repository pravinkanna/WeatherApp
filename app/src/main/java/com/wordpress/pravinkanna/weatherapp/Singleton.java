package com.wordpress.pravinkanna.weatherapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {
    private static Singleton mInstance;
    private RequestQueue mRequestQueue;

    public Singleton(Context context){
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized Singleton getInstance(Context context){
        if (mInstance == null) {
            mInstance = new Singleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

}
