package com.snwnw.snwnw.presentation.FCN;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.presentation.ui.activities.testAcitivty;


/**
 * Created by afaf.elshafey on 11/8/2017.
 */


public class MyFCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i("iamremote","remotemessage") ;
        if (remoteMessage.getData().size() > 0) {

            String BODY = remoteMessage.getData().get("body");
         //   int type = Integer.parseInt(remoteMessage.getData().get("type"));
///            int usertype = Integer.parseInt(remoteMessage.getData().get("userType"));
         //   sendNotification(remoteMessage.getNotification().getBody());
            sendNotification(BODY);

        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, testAcitivty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ekleel")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}