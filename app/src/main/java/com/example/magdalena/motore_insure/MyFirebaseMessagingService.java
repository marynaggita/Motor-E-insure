package com.example.magdalena.motore_insure;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final  String TAG ="MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "FROM: " +remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0){
            Log.d(TAG,"Message data: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG,"Message data: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
        }
        private void sendNotification(String body){

        Intent intent = new Intent(this,HomeActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
        .setContentTitle("Message from MotorE-Insure")
        .setContentText(body)
        .setAutoCancel(true)

        //notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =(NotificationManager)  getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());


    }
}




