package com.snwnw.snwnw.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.password_model;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.domain.models.social_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.rigsterPresenterListener;
import com.snwnw.snwnw.presentation.ui.activities.registerActivity;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by afaf.elshafey on 7/24/2017.
 */

public class RegisterPresenter {

    Context context;
    private final LoginPresenterListener mListener;
    SNWNWServices snwnwServices;
    HashMap<String, Object> jsonParams;
   // registerActivity activity;

    public RegisterPresenter(LoginPresenterListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
      //  activity = (registerActivity) context;
    }

    public void registerNewUser(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

       snwnwServices.getAPI().CreateUser(jsonParams, "application/json").enqueue(new Callback<register_result_model>() {
            @Override
            public void onResponse(Call<register_result_model> call, Response<register_result_model> response) {
                register_result_model user_data = response.body();
                Log.i("google", "success");
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    Log.i("google",user_data.getUser().getToken());
                    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }

                else {

                    try {
                        dialog.dismiss();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error_msg = jObjError.getString("msg");
                        mListener.onLoginFailed(error_msg);
                        Toast.makeText(context,error_msg+"",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //}

            }

            @Override
            public void onFailure(Call<register_result_model> call, Throwable t) {
                mListener.onRegistrFailed(t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void LoginUser(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().LoginUser(jsonParams, "application/json").enqueue(new Callback<register_result_model>() {
            @Override
            public void onResponse(Call<register_result_model> call, Response<register_result_model> response) {
                register_result_model user_data = response.body();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    mListener.onLoginSuccess(user_data.getUser());

                }

                else {

                    try {
                        dialog.dismiss();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error_msg = jObjError.getString("msg");
                        mListener.onLoginFailed(error_msg);
                        Toast.makeText(context,error_msg+"",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //}

            }

            @Override
            public void onFailure(Call<register_result_model> call, Throwable t) {
                mListener.onLoginFailed(t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void registerSocial(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().CreateSocialUser(jsonParams, "application/json").enqueue(new Callback<social_model>() {
            @Override
            public void onResponse(Call<social_model> call, Response<social_model> response) {
                social_model user_data = response.body();
                Log.i("google", "success");
                Log.d("google","register social "+response.body().getUser_model().getId());
                SNWNWPrefs prefs = new SNWNWPrefs();
                prefs.set_Int_value(Constants.UserId, response.body().getUser_model().getId(), getApplicationContext());
                dialog.dismiss();
                if (response.isSuccessful()) {
                    Log.i("google",user_data.getToken());
                    mListener.onRegisterSuccess(user_data.getToken());
                }

                else {

                    try {
                        dialog.dismiss();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error_msg = jObjError.getString("msg");
                        mListener.onLoginFailed(error_msg);
                        Log.i("google",error_msg);
                        Toast.makeText(context,error_msg+"",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //}

            }

            @Override
            public void onFailure(Call<social_model> call, Throwable t) {
                mListener.onRegistrFailed(t.getMessage());
                Log.i("iameeerrrror",t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void forgetPassword(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().ForgetPass(jsonParams, "application/json").enqueue(new Callback<password_model>() {
            @Override
            public void onResponse(Call<password_model> call, Response<password_model> response) {
                password_model user_data = response.body();
                dialog.dismiss();
                if (response.isSuccessful()) {
                   // mListener.onLoginSuccess(user_data.getUser());
                    mListener.onForgetPassSuccess(user_data.getMsg());
                }

                else {

                    try {
                        dialog.dismiss();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error_msg = jObjError.getString("msg");
                        mListener.onLoginFailed(error_msg);
                        Toast.makeText(context,error_msg+"",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //}

            }

            @Override
            public void onFailure(Call<password_model> call, Throwable t) {
                mListener.onLoginFailed(t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }


    public void loadHomeCountries(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().homeCountries(jsonParams,"application/json").enqueue(new Callback<homeCountriesModel>() {
            @Override
            public void onResponse(Call<homeCountriesModel> call, Response<homeCountriesModel> response) {

                dialog.dismiss();
                if (response.isSuccessful()){

                    homeCountriesModel result = response.body();
                    mListener.onHomeCountriesLoadedSuccess(result);

                    Log.i("iamcountties",result.getCountries().size()+"") ;
                }
                else {

                 //   mListener.onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<homeCountriesModel> call, Throwable t) {

                dialog.dismiss();
                mListener.onHomeCountriesLoadedFailed(t.getMessage());
            }
        });

    }
    public void loadCities(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().homeCities(jsonParams,"application/json").enqueue(new Callback<homeCitiesModel>() {
            @Override
            public void onResponse(Call<homeCitiesModel> call, Response<homeCitiesModel> response) {

                dialog.dismiss();
                if (response.isSuccessful()){

                    homeCitiesModel result = response.body();
                    mListener.onHomeCitiesLoadedSuccess(result);

                    Log.i("citieeeees",result.getCountries().size()+"") ;
                }
                else {

                    //   mListener.onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<homeCitiesModel> call, Throwable t) {

                dialog.dismiss();
                mListener.onHomeCitiesLoadedFailed(t.getMessage());
            }
        });

    }

}