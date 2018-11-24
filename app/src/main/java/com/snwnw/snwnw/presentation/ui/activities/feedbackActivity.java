package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.FAQ_result_model;
import com.snwnw.snwnw.presentation.presenters.impl.feedbackPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.codePresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.faq_feedback_Preseneter_Listener;
import com.snwnw.snwnw.presentation.utils.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 3/10/2018.
 */

public class feedbackActivity extends AppCompatActivity implements faq_feedback_Preseneter_Listener {
    @BindView(R.id.enter_your_name)
    EditText enter_your_name;
    @BindView(R.id.enter_your_email)
    EditText enter_your_email;
    @BindView(R.id.enter_your_opinion)
    EditText enter_your_opinion;
    @BindView(R.id.send_btn)
    Button send_btn;
    @BindView(R.id.nav_icon)
    ImageView nav_icon;
    public static int goProfile =100 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (enter_your_name.getText().toString().trim().length()==0){
                    enter_your_name.setError("");
                }
                else if (enter_your_email.getText().toString().trim().length()==0){
                    enter_your_email.setError("");
                }
                else if (enter_your_opinion.getText().toString().trim().length()==0){
                    enter_your_opinion.setError("");
                }
                else {

                    sendFeeback();
                }
            }
        });
    }

    public void sendFeeback(){

        feedbackPresenter presenter = new feedbackPresenter(feedbackActivity.this,feedbackActivity.this);
        HashMap<String,Object>Params = new HashMap<>() ;
        Params.put("name",enter_your_name.getText().toString().trim());
        Params.put("email",enter_your_email.getText().toString().trim());
        Params.put("mobile","010");
        Params.put("title","snwnw");
        Params.put("message",enter_your_opinion.getText().toString().trim());
        presenter.sendMyOpinion(Params);
    }

    @Override
    public void onTokenSuccess(String result) {

        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onTokenFailed(int code, String error) {

        if (code== Constants.ExpiredToken){
            Intent go = new Intent(feedbackActivity.this, InternalLoginActivity.class);
            startActivityForResult(go,goProfile);
        }
        else {
            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFAQSLoaded(FAQ_result_model model) {

    }

    @Override
    public void onFAQSFailed(int code, String result) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
