package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.snwnw.snwnw.R;
import com.snwnw.snwnw.presentation.ui.profilePackage.myDataActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 6/25/2018.
 */

public class editPasswordActivity  extends AppCompatActivity{

    @BindView(R.id.edt_btn)
    Button edt_btn;
    @BindView(R.id.nav_icon)
    ImageView nav_icon;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        ButterKnife.bind(this) ;

        nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edt_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent gotoUpdate = new Intent(editPasswordActivity.this,updatePasswordActivity.class);
                startActivity(gotoUpdate);
            }
        });
    }
}
