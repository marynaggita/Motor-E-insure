package com.example.magdalena.motore_insure;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonParternQueue {

    private static SingletonParternQueue mInstance;
    private RequestQueue mRequestQueue;

    private SingletonParternQueue(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized SingletonParternQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonParternQueue(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public<T> void addToRequestQue(Request<T> request) {
        mRequestQueue.add(request);
    }
}
