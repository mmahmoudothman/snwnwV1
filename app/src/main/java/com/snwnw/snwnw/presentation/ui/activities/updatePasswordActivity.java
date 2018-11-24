package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.presentation.presenters.impl.passwordPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.passwordPresenterListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 3/15/2018.
 */

public class updatePasswordActivity extends AppCompatActivity implements passwordPresenterListener {
    @BindView(R.id.enter_your_code)
    EditText current_password;
    @BindView(R.id.enter_your_passwrod)
    EditText new_password ;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.nav_icon)
    ImageView nav_icon;
    public static int goProfile =100 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_password.getText().toString().trim().length()==0){
                    current_password.setError("");
                }
                else if (new_password.getText().toString().trim().length()==0){
                    new_password.setError("");
                }
                else {
                    UpdatePassword();
                }
            }
        });

        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void UpdatePassword(){
        passwordPresenter presenter = new passwordPresenter(updatePasswordActivity.this,updatePasswordActivity.this);
        HashMap<String,Object>Params = new HashMap<>();
        Params.put("old_password",current_password.getText().toString().trim());
        Params.put("password",new_password.getText().toString().trim());
        presenter.updatePassword(Params);
    }

    @Override
    public void expiredToken(int Code) {
        Intent go = new Intent(updatePasswordActivity.this, InternalLoginActivity.class);
        startActivityForResult(go,goProfile);

    }

    @Override
    public void onUpdateDone(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
