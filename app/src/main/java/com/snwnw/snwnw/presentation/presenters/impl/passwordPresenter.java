package com.snwnw.snwnw.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.code_result;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.passwordPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.profilePresenterListener;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fifi elshafie on 2/6/2018.
 */

public class passwordPresenter {

    Context context;
    private final passwordPresenterListener mListener;
    SNWNWServices snwnwServices;
    HashMap<String, Object> jsonParams;


    public passwordPresenter(passwordPresenterListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
        //  activity = (registerActivity) context;
    }

    public void updatePasswordbycode(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().updatePasswordbycode(jsonParams, "application/json").enqueue(new Callback<code_result>() {
            @Override
            public void onResponse(Call<code_result> call, Response<code_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    code_result user_data = response.body();
                    mListener.onUpdateDone(user_data.getCode());
                  //  mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    if (response.code()== Constants.ExpiredToken){

                        mListener.expiredToken(response.code());
                    }
                  /*  try {
                        dialog.dismiss();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error_msg = jObjError.getString("msg");
                        mListener.onLoginFailed(error_msg);
                        Toast.makeText(context,error_msg+"",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
                //}

            }

            @Override
            public void onFailure(Call<code_result> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }


    public void updatePassword(HashMap<String, Object> jsonParams) {
        String AuthorizedToken = "Bearer"+" "+ SNWNWPrefs.getDefaults(Constants.Token,context);

        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().updatePassword(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<code_result>() {
            @Override
            public void onResponse(Call<code_result> call, Response<code_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    code_result user_data = response.body();
                    mListener.onUpdateDone(user_data.getCode());
                    //  mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    if (response.code()== Constants.ExpiredToken){

                        mListener.expiredToken(response.code());
                    }
                  /*  try {
                        dialog.dismiss();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error_msg = jObjError.getString("msg");
                        mListener.onLoginFailed(error_msg);
                        Toast.makeText(context,error_msg+"",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
                //}

            }

            @Override
            public void onFailure(Call<code_result> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

}
