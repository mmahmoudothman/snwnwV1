package com.snwnw.snwnw.presentation.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.presentation.presenters.impl.passwordPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.passwordPresenterListener;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.PATCH;

/**
 * Created by fifi elshafie on 3/13/2018.
 */

public class CodeForgetPasswordActivity extends AppCompatActivity implements passwordPresenterListener {
    @BindView(R.id.enter_your_passwrod)
    EditText enter_your_passwrod;
    @BindView(R.id.ed_repeat_password)
    EditText ed_repeat_password;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.enter_your_code)
    EditText enter_your_code;
    String Email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_forge_pass);
        ButterKnife.bind(this);
        Email = getIntent().getStringExtra("email");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!enter_your_passwrod.getText().toString().trim().equals(ed_repeat_password.getText().toString().trim())){
                    // Send New PAssword
                    enter_your_passwrod.setError(getResources().getString(R.string.pass_not_equal));

                }
                else if (enter_your_code.getText().toString().trim().length()==0){
                    enter_your_code.setError("");
                }
                else {

                    HashMap<String,Object>Params = new HashMap<>();
                    Params.put("code",enter_your_code.getText().toString().trim()) ;
                    Params.put("email",Email);
                    Params.put("password",enter_your_passwrod.getText().toString().trim());
                    String AuthorizationToken = "Bearer"+" "+ SNWNWPrefs.getDefaults(Constants.Token,getApplicationContext());

                    passwordPresenter presenter = new passwordPresenter(CodeForgetPasswordActivity.this,CodeForgetPasswordActivity.this);
                    presenter.updatePasswordbycode(Params);

                            //onUpdateDone

                }
            }
        });
    }

    @Override
    public void expiredToken(int Code) {
        Toast.makeText(getApplicationContext(),Code+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpdateDone(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        finish();
    }
}
