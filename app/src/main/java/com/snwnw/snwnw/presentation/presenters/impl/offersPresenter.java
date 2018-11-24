package com.snwnw.snwnw.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.add_place_model;
import com.snwnw.snwnw.domain.models.fav_result;
import com.snwnw.snwnw.domain.models.offerModel;
import com.snwnw.snwnw.domain.models.place_details_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.offersPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.profilePresenterListener;
import com.snwnw.snwnw.presentation.utils.Constants;

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

public class offersPresenter {

    Context context;
    private final offersPresenterListener mListener;
    SNWNWServices snwnwServices;
    HashMap<String, Object> jsonParams;


    public offersPresenter(offersPresenterListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
    }

    public void getOffers(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().Offers(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<offerModel>() {
            @Override
            public void onResponse(Call<offerModel> call, Response<offerModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    offerModel user_data = response.body();
                    //  Toast.makeText(context,user_data.(),Toast.LENGTH_SHORT).show();
                    mListener.onOffersLoaded(user_data);
                    // mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    mListener.onFailed(response.code(),response.message());
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
            public void onFailure(Call<offerModel> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

}
