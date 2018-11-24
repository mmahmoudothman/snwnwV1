package com.snwnw.snwnw.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.user_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.ui.adapters.CustomAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.CustomArrayAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.citiesAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.countryAdapter;
import com.snwnw.snwnw.presentation.ui.adapters.spinnerArrayAdapter;
import com.snwnw.snwnw.presentation.utils.Connectivity;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 1/9/2018.
 */
public class registerActivity extends AppCompatActivity implements LoginPresenterListener {
    @BindView(R.id.btn_register)
    TextView btn_register;
    @BindView(R.id.nav_icon)
    ImageView nav_icon;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.lastname)
    EditText lastname;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    /*@BindView(R.id.confirmpassword)
    EditText confirmpassword;*/
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.ccp)
    Spinner ccp;
    @BindView(R.id.edt_mobile_num)
    EditText edt_mobile_num;
    @BindView(R.id.city_spinner)
    Spinner city_spinner;
    ArrayList<homeCountriesModel.countryModel>countries ;
    ArrayList<homeCitiesModel.countryModel> cities;


    private boolean showpasss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        countries = new ArrayList<>();
        showpasss = true;
        cities = new ArrayList<>() ;
        btn_register.setText(getResources().getString(R.string.enter));
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gologin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        getHomeCountries();
        getCities();
    }

    public void Register() {
        HashMap<String, Object> Params = new HashMap<>();
        Params.put("first_name", username.getText().toString().trim());
        Params.put("last_name", lastname.getText().toString().trim());
        Params.put("password", password.getText().toString().trim());
        Params.put("email", email.getText().toString().trim());
        Params.put("home_country",countries.get(ccp.getSelectedItemPosition()).getId());
       Params.put("city_id",cities.get(city_spinner.getSelectedItemPosition()).getId());
        Params.put("register_id", "123e4vvvv6755446");
        Params.put("device_id", "1255553e45vvvv6");
        Params.put("platform", "android");
        Params.put("version", "0");
        Params.put("mobile",edt_mobile_num.getText().toString().trim());

        RegisterPresenter presenter = new RegisterPresenter(registerActivity.this, registerActivity.this);
        presenter.registerNewUser(Params);
    }

    public void getHomeCountries(){
        HashMap<String, Object> Params = new HashMap<>();
       // Params.put("offset", 0);
       // Params.put("take", 10);
        Params.put("lang", "ar");
        RegisterPresenter presenter = new RegisterPresenter(registerActivity.this, registerActivity.this);
        presenter.loadHomeCountries(Params);

    }

    public void getCities(){
        HashMap<String, Object> Params = new HashMap<>();
        // Params.put("offset", 0);
        // Params.put("take", 10);
        Params.put("lang", "ar");
        RegisterPresenter presenter = new RegisterPresenter(registerActivity.this, registerActivity.this);
        presenter.loadCities(Params);

    }

    public void validateData() {
        if (username.getText().toString().trim().length() == 0) {
            username.setError(getResources().getString(R.string.user_name_error));
        } else if (lastname.getText().toString().trim().length() == 0) {
            lastname.setError(getResources().getString(R.string.last_name_error));
        } else if (email.getText().toString().trim().length() == 0) {
            email.setError(getResources().getString(R.string.email_error));
        } else if (password.getText().toString().trim().length() == 0) {
            password.setError(getResources().getString(R.string.pass_error));
        }

        /*else if (confirmpassword.getText().toString().trim().length() == 0) {
            confirmpassword.setError(getResources().getString(R.string.confirmpass_error));
        } else if (!password.getText().toString().trim().equals(confirmpassword.getText().toString().trim())) {
            confirmpassword.setError(getResources().getString(R.string.equalpass_error));
        } */

        else if (Connectivity.isConnected(registerActivity.this)) {
            Register();
            // testing
          //  Intent go = new Intent(registerActivity.this,registerCodeActivity.class);
          //  startActivity(go);
        }
        else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRegisterSuccess(String Token) {
        saveUser(Token);
        Intent go = new Intent(registerActivity.this,registerCodeActivity.class);
        startActivity(go);
        finish();
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

    }

    @Override
    public void onForgetPassFail(String Msg) {

    }

    @Override
    public void onHomeCountriesLoadedSuccess(homeCountriesModel model) {

        countries = model.getCountries() ;
        loadHomeCountrySpinner(model.getCountries());
    }


    @Override
    public void onHomeCountriesLoadedFailed(String error) {

    }

    @Override
    public void onHomeCitiesLoadedSuccess(homeCitiesModel model) {

        cities = model.getCountries() ;
        Log.i("iamciires",model.getCountries().size()+"");
        loadHomeCitiesSpinner(model.getCountries());

    }

    @Override
    public void onHomeCitiesLoadedFailed(String error) {

    }

    public void saveUser(String Token) {
        SNWNWPrefs prefs = new SNWNWPrefs();
        prefs.setDefaults(Constants.Token, Token, getApplicationContext());
    }
    public void gologin() {
        Intent intent = new Intent(registerActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void loadHomeCountrySpinner(ArrayList<homeCountriesModel.countryModel>Countries){
        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),Countries);
        ccp.setAdapter(customAdapter);
    }
    public void loadHomeCitiesSpinner(ArrayList<homeCitiesModel.countryModel>Countries){
        citiesAdapter customAdapter=new citiesAdapter(getApplicationContext(),Countries);
        city_spinner.setAdapter(customAdapter);

    }

    public void showPass(View view) {
        if (showpasss){
            password.setTransformationMethod(null);
            showpasss = false;

        }else{
            password.setTransformationMethod(new PasswordTransformationMethod());
            showpasss = true;
        }

    }
}
