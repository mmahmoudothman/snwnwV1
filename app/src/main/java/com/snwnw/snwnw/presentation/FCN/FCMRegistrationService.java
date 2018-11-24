package com.snwnw.snwnw.presentation.FCN;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

/**
 * Created by afaf.elshafey on 11/8/2017.
 */


public class FCMRegistrationService extends IntentService {
    SharedPreferences preferences;

    public FCMRegistrationService() {
        super("FCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // get Default Shard Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // get token from Firebase
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("iamnnnnewtoken",token);
        SNWNWPrefs.setDefaults(Constants.RegistrationToken,token,this);
        // check if intent is null or not if it isn't null we will ger refreshed value and
        // if its true we will override token_sent value to false and apply
        if (intent.getExtras() != null) {
            boolean refreshed = intent.getExtras().getBoolean("refreshed");
            if (refreshed) preferences.edit().putBoolean("token_sent", false).apply();
        }

        // if token_sent value is false then use method sendTokenToServer to send token to server
        //if (!preferences.getBoolean("token_sent", false));
         //   sendTokenToServer(token);
    }

}