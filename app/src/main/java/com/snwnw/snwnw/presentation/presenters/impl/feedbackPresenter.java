package com.snwnw.snwnw.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.FAQ_result_model;
import com.snwnw.snwnw.domain.models.code_result;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.codePresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.faq_feedback_Preseneter_Listener;
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

public class feedbackPresenter {

    Context context;
    private final faq_feedback_Preseneter_Listener mListener;
    SNWNWServices snwnwServices;
    HashMap<String, Object> jsonParams;


    public feedbackPresenter(faq_feedback_Preseneter_Listener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
        //  activity = (registerActivity) context;
    }

    public void sendMyOpinion(HashMap<String, Object> jsonParams) {
        String AuthorizationToken = "Bearer"+" "+ SNWNWPrefs.getDefaults(Constants.Token,context);
        Log.i("iamautho",AuthorizationToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().sendOpinion(jsonParams, "application/json",AuthorizationToken).enqueue(new Callback<code_result>() {
            @Override
            public void onResponse(Call<code_result> call, Response<code_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {

                    code_result user_data = response.body();
                    mListener.onTokenSuccess(user_data.getCode());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    // if (response.code()== Constants.ExpiredToken){
                    mListener.onTokenFailed(response.code(),response.message());
                    //  }
                }
            }

            @Override
            public void onFailure(Call<code_result> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                mListener.onTokenFailed(t.hashCode(),t.getMessage());

            }
        });

    }


    public void getFaq(HashMap<String, Object> jsonParams) {
        String AuthorizationToken = "Bearer"+" "+ SNWNWPrefs.getDefaults(Constants.Token,context);
        Log.i("iamautho",AuthorizationToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        snwnwServices.getAPI().getFAQS(jsonParams, "application/json",AuthorizationToken).enqueue(new Callback<FAQ_result_model>() {
            @Override
            public void onResponse(Call<FAQ_result_model> call, Response<FAQ_result_model> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {

                    FAQ_result_model user_data = response.body();
                    mListener.onFAQSLoaded(user_data);
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    // if (response.code()== Constants.ExpiredToken){
                    mListener.onFAQSFailed(response.code(),response.message());
                    //  }
                }
            }

            @Override
            public void onFailure(Call<FAQ_result_model> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                mListener.onFAQSFailed(t.hashCode(),t.getMessage());

            }
        });

    }
}
