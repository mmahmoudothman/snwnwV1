package com.snwnw.snwnw.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.add_place_model;
import com.snwnw.snwnw.domain.models.fav_result;
import com.snwnw.snwnw.domain.models.mapsPlacesLoaderModel;
import com.snwnw.snwnw.domain.models.place_details_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.mapSearchPlacesListener;
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

public class mapSearchPlacesPresenter {

    Context context;
    private final mapSearchPlacesListener mListener;
    SNWNWServices snwnwServices;


    public mapSearchPlacesPresenter(mapSearchPlacesListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
    }

    public void getPlaces(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().getPlaces(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<mapsPlacesLoaderModel>() {
            @Override
            public void onResponse(Call<mapsPlacesLoaderModel> call, Response<mapsPlacesLoaderModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    mapsPlacesLoaderModel user_data = response.body();
                    mListener.onMapPlacesLoaded(user_data);
                }
                else {
                    Log.i("iamerrroe","sub");
                    Log.i("iamerrroe","errror"+response.code()+response);
                    mListener.onMapPlacesFailed(response.code(),response.message());
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
            public void onFailure(Call<mapsPlacesLoaderModel> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }



}
