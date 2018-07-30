package com.example.magdalena.motore_insure;

import android.app.Service;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseIDService extends FirebaseInstanceIdService {
    private  static  final  String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,"New Token: "+ recent_token );
    }
}
