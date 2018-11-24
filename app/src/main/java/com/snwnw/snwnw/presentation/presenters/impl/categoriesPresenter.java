package com.snwnw.snwnw.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.add_place_model;
import com.snwnw.snwnw.domain.models.categoriesResult;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.profilePresenterListener;

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

public class categoriesPresenter {

    Context context;
    private final placesPrestenerListener mListener;
    SNWNWServices snwnwServices;
    HashMap<String, Object> jsonParams;


    public categoriesPresenter(placesPrestenerListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
    }

    public void getCategories(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().getCategories(jsonParams, "application/json").enqueue(new Callback<categoriesResult>() {
            @Override
            public void onResponse(Call<categoriesResult> call, Response<categoriesResult> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    categoriesResult user_data = response.body();
                    Log.i("iamcateogire",user_data.getAllServices().size()+"");
                    mListener.getAllCategories(user_data.getAllServices());
                    // mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
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
            public void onFailure(Call<categoriesResult> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void getSubCategories(HashMap<String, Object> jsonParams) {
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().getCategories(jsonParams, "application/json").enqueue(new Callback<categoriesResult>() {
            @Override
            public void onResponse(Call<categoriesResult> call, Response<categoriesResult> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    categoriesResult user_data = response.body();
                    Log.i("iamcateogire",user_data.getAllServices().size()+"");
                    mListener.getAllSubCategories(user_data.getAllServices());
                    // mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
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
            public void onFailure(Call<categoriesResult> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

}
