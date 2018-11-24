package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.user_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 3/6/2018.
 */

public class forgetpasswordActivity extends AppCompatActivity implements LoginPresenterListener {
    @BindView(R.id.enter_your_email)
    EditText enter_your_email;
    @BindView(R.id.send_email_btn)
    Button send_email_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        send_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enter_your_email.getText().toString().trim().length()!=0){
                    // send email
                    forgetPassword(enter_your_email.getText().toString().trim());
                }
                else {
                    enter_your_email.setError("");
                }
            }
        });
    }

    public void forgetPassword(String Email){
        RegisterPresenter presenter = new RegisterPresenter(forgetpasswordActivity.this,forgetpasswordActivity.this);
        HashMap<String,Object>Params = new HashMap<>() ;
        Params.put("email",Email);
        presenter.forgetPassword(Params);
    }

    @Override
    public void onRegisterSuccess(String Token) {

    }

    @Override
    public void onRegistrFailed(String Error) {

    }

    @Override
    public void onLoginSuccess(user_model user_model) {

    }

    @Override
    public void onLoginFailed(String Error) {

    }

    @Override
    public void onForgetPassSuccess(String Msg) {

        Toast.makeText(getApplicationContext(),Msg,Toast.LENGTH_SHORT).show();
        Intent goToCode = new Intent(forgetpasswordActivity.this,CodeForgetPasswordActivity.class);
        goToCode.putExtra("email",enter_your_email.getText().toString().trim());
        startActivity(goToCode);
        finish();
    }

    @Override
    public void onForgetPassFail(String Msg) {
        Toast.makeText(getApplicationContext(),Msg,Toast.LENGTH_SHORT).show();

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
}
