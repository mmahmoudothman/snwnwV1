package com.snwnw.snwnw.presentation.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.presentation.presenters.impl.codePresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.codePresenterListener;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 3/10/2018.
 */

public class settingActivity extends AppCompatActivity implements codePresenterListener {

    @BindView(R.id.edit_num1)
    EditText edit_num1;
    @BindView(R.id.edit_num2)
    EditText edit_num2;
    @BindView(R.id.edit_num3)
    EditText edit_num3;
    @BindView(R.id.edit_num4)
    EditText edit_num4;
    @BindView(R.id.edit_num5)
    EditText edit_num5;
    @BindView(R.id.edit_num6)
    EditText edit_num6;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.resend_code)
    TextView resend_code;
    @BindView(R.id.nav_icon)
    ImageView nav_icon;
    @BindView(R.id.confirmation_code_layout)
    LinearLayout confirmation_code_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        if (SNWNWPrefs.get_int_value(Constants.ISACTIVIE,getApplicationContext())==0){
            confirmation_code_layout.setVisibility(View.VISIBLE);
        }
        else {
            confirmation_code_layout.setVisibility(View.GONE);
        }
        setUnderLine(resend_code);
        resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codePresenter presenter = new codePresenter(settingActivity.this,settingActivity.this);
                presenter.resendCode();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
            }
        });



        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {

                    edit_num2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    edit_num3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        edit_num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() > 0) {
                    edit_num4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        edit_num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() > 0) {
                    edit_num5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        edit_num5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() > 0) {
                    edit_num6.requestFocus();
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        edit_num6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             /*   if (charSequence.toString().length() > 0) {
                    edit_num4.requestFocus();
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }
    public void setUnderLine(TextView textView){
        SpannableString content = new SpannableString(getResources().getString(R.string.re_confirmation_code));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

    }

    @Override
    public void onTokenSuccess(String result) {
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        SNWNWPrefs.set_Int_value(Constants.ISACTIVIE,1,getApplicationContext());
        finish();
    }

    @Override
    public void onTokenFailed(int code, String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCodeResent(String result) {

    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

    }
    public void sendCode() {
        codePresenter presenter = new codePresenter(settingActivity.this, settingActivity.this);
        HashMap<String, Object> Params = new HashMap<>();
        String Code = edit_num1.getText().toString().trim() +
                edit_num2.getText().toString().trim() +
                edit_num3.getText().toString().trim() +
                edit_num4.getText().toString().trim() +
                edit_num5.getText().toString().trim() +
                edit_num6.getText().toString().trim() ;
        Params.put("code", Code);
        presenter.sendCode(Params);
    }
}
