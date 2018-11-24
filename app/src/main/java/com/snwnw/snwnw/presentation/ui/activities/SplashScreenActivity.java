package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.iid.FirebaseInstanceId;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;
//import javax.inject.Inject;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import retrofit2.Retrofit;

public class SplashScreenActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 2000;
//    @BindView(R.id.promotion_splash)
//    ImageView promotionSplash;
//    @Inject
//    Retrofit retrofit;
//    @Inject
//    Picasso picasso;
//    @Inject
//    SharedPreferences sharedPreferences;
//    MainPresenter mainPresenter;
//    Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.e("Tokenis", FirebaseInstanceId.getInstance().getToken()+"fifi");
//        ADRApp app = (ADRApp) getApplication();
//        app.getApiComponent().inject(this);
//        ButterKnife.bind(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToHomeScreen();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
    /**
     * navigate to home screen
     */
    private void goToHomeScreen() {
        String Token = SNWNWPrefs.getDefaults(Constants.Token,SplashScreenActivity.this);
        int USERID = SNWNWPrefs.get_int_value(Constants.UserId,SplashScreenActivity.this);


        Log.d("google","this is the token  "+Token);
        Log.d("google","this is the user id   "+USERID);

        if (USERID!=0 &&Token.length()!=0){
           Intent mainIntent = new Intent(SplashScreenActivity.this, testAcitivty.class);
            SplashScreenActivity.this.startActivity(mainIntent);
            SplashScreenActivity.this.finish();

          /*  int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                .build(this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }
*/

        }
        else {
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(mainIntent);
            SplashScreenActivity.this.finish();
        }
    }



}
