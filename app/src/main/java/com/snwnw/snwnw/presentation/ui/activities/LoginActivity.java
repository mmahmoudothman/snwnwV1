package com.snwnw.snwnw.presentation.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.controllers.WSController;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.user_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.utils.Connectivity;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 1/14/2018.
 */

public class LoginActivity extends AppCompatActivity implements LoginPresenterListener {
    @BindView(R.id.forget_password)
    TextView forget_password;
    @BindView(R.id.btn_register)
    TextView btn_register;
    @BindView(R.id.nav_icon)
    ImageView nav_icon;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.userpassword)
    EditText userpassword;
    @BindView(R.id.user_email)
    EditText user_email;
    String RegToken = "";
    public static final int REQUEST_PHONE = 100;
    String deviceImei = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        RegToken = FirebaseInstanceId.getInstance().getToken();
        checkPermissions();
        seUnderLine(forget_password);
        btn_register.setText(getResources().getString(R.string.register));
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegister();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginNow();
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,forgetpasswordActivity.class);
                startActivity(intent);
            }
        });

        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_register.performClick();
            }
        });
    }


    public void seUnderLine(TextView view) {
        SpannableString content = new SpannableString(getResources().getString(R.string.forget_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        view.setText(content);
    }

    public void goRegister() {
        Intent intent = new Intent(LoginActivity.this, registerActivity.class);
        startActivity(intent);
        finish();
    }

    public void LoginNow() {

        if (Connectivity.isConnected(LoginActivity.this) == true) {

            if (user_email.getText().toString().trim().length() == 0) {
                user_email.setError(getResources().getString(R.string.email_error));
            } else if (userpassword.getText().toString().trim().length() == 0) {
                userpassword.setError(getResources().getString(R.string.pass_error));
            } else {
                HashMap<String, Object> Params = new HashMap<>();
                Params.put("password", userpassword.getText().toString().trim());
                Params.put("email", user_email.getText().toString().trim());
                if (RegToken != null) {
                    Params.put("register_id", RegToken);
                } else {
                    Params.put("register_id", FirebaseInstanceId.getInstance().getToken());
                }
               // Params.put("device_id", deviceImei);
                Params.put("device_id", "1255553eff45vvvv6");
                Params.put("platform", "android");
                Params.put("version", "0");
                Log.i("loginparams", Params + "");

                RegisterPresenter presenter = new RegisterPresenter(LoginActivity.this, LoginActivity.this);
                presenter.LoginUser(Params);
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRegisterSuccess(String Token) {

    }

    @Override
    public void onRegistrFailed(String Error) {

    }

    @Override
    public void onLoginSuccess(user_model user_model) {
            SNWNWPrefs.setDefaults(Constants.Token, user_model.getToken(), LoginActivity.this);
            SNWNWPrefs.set_Int_value(Constants.UserId, user_model.getId(), LoginActivity.this);
            SNWNWPrefs.set_Int_value(Constants.ISACTIVIE,user_model.getIs_active(),LoginActivity.this);
            goMenu();
    }

    @Override
    public void onLoginFailed(String Error) {
        Log.i("iamloginsucess", "iamfailed" + "");
        Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onForgetPassSuccess(String Msg) {

    }

    @Override
    public void onForgetPassFail(String Msg) {

    }

    @Override
    public void onHomeCountriesLoadedSuccess(homeCountriesModel model) {

    }

    @Override
    public void onHomeCountriesLoadedFailed(String error) {

    }

    @Override
    public void onHomeCitiesLoadedSuccess(homeCitiesModel model) {

    }

    @Override
    public void onHomeCitiesLoadedFailed(String error) {

    }

    public void goMenu() {
        Intent go = new Intent(LoginActivity.this, testAcitivty.class);
        startActivity(go);
        finish();
    }

    public void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE);
            } else {

                deviceImei();
            }

        }
        else {
            deviceImei();
        }
    }

    public void deviceImei() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        deviceImei = telephonyManager.getDeviceId();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    deviceImei();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
