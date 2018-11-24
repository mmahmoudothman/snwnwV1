package com.snwnw.snwnw.presentation.FCN;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by afaf.elshafey on 11/8/2017.
 */

public class FCMTokenRefreshListenerService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, FCMRegistrationService.class);

        Log.i("iamthetoken" , FirebaseInstanceId.getInstance().getToken());
        intent.putExtra("refreshed", true);
        startService(intent);
    }
}