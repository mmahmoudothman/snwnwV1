package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.FAQ_result_model;
import com.snwnw.snwnw.presentation.presenters.impl.feedbackPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.faq_feedback_Preseneter_Listener;
import com.snwnw.snwnw.presentation.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 3/10/2018.
 */

public class FQAActivity extends AppCompatActivity implements faq_feedback_Preseneter_Listener {
    public static int goProfile =100 ;
    //lvExp
    @BindView(R.id.faqList)
    ExpandableListView faqList;
    @BindView(R.id.nav_icon)
    ImageView nav_icon;
    @BindView(R.id.enter_your_question)
    EditText enter_your_question;
    @BindView(R.id.send_btn)
    Button send_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fqa);
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
                if (enter_your_question.getText().toString().trim().length()==0){

                    enter_your_question.setError("");
                }
                else {

                    sendFeeback();
                }
            }
        });
        getFAQS();

    }

    public void getFAQS(){
        feedbackPresenter presenter = new feedbackPresenter(FQAActivity.this,FQAActivity.this);
        HashMap<String,Object>Params = new HashMap<>();
        Params.put("offset",0);
        Params.put("take",10);
        Params.put("lang","ar");
        presenter.getFaq(Params);
    }

    @Override
    public void onTokenSuccess(String resultcode) {

        Toast.makeText(getApplicationContext(),resultcode,Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onTokenFailed(int code, String Result) {

        if (code== Constants.ExpiredToken){
            Intent go = new Intent(FQAActivity.this, InternalLoginActivity.class);
            startActivityForResult(go,goProfile);

        }
    }

    @Override
    public void onFAQSLoaded(FAQ_result_model model) {
        ArrayList<String>headerList = new ArrayList<>();
        for (int i =0 ;i<model.getAllFQS().size() ;i++){
            headerList.add(model.getAllFQS().get(i).getQuestion());
        }


        ExpandableListAdapter adapter = new com.snwnw.snwnw.presentation.ui.adapters.ExpandableListAdapter(FQAActivity.this,headerList,
                model.getAllFQS());

        faqList.setAdapter(adapter);

        Log.i("faqssss",model.getAllFQS().size()+"");
    }

    @Override
    public void onFAQSFailed(int code, String result) {

        if (code== Constants.ExpiredToken){
            Intent go = new Intent(FQAActivity.this, InternalLoginActivity.class);
            startActivityForResult(go,goProfile);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getFAQS();
    }

    public void sendFeeback(){

        feedbackPresenter presenter = new feedbackPresenter(FQAActivity.this,FQAActivity.this);
        HashMap<String,Object>Params = new HashMap<>() ;
        Params.put("name","snwnw");
        Params.put("email","snwnw@snwnw.com");
        Params.put("mobile","010");
        Params.put("title","snwnw");
        Params.put("message",enter_your_question.getText().toString().trim());
        presenter.sendMyOpinion(Params);
    }

}
