package com.snwnw.snwnw.presentation.presenters.impl;

/**
 * Created by fifi elshafie on 6/30/2018.
 */

import android.content.Context;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.add_place_model;
import com.snwnw.snwnw.domain.models.fav_result;
import com.snwnw.snwnw.domain.models.place_details_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.presentation.presenters.interfaces.placesPrestenerListener;
import com.snwnw.snwnw.presentation.presenters.interfaces.searchPresenterListener;
import com.snwnw.snwnw.presentation.utils.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.snwnw.snwnw.domain.interactors.SNWNWServices;
import com.snwnw.snwnw.domain.models.add_place_model;
import com.snwnw.snwnw.domain.models.fav_result;
import com.snwnw.snwnw.domain.models.place_details_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
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

public class searchPresenter {

    Context context;
    private final searchPresenterListener mListener;
    SNWNWServices snwnwServices;
    HashMap<String, Object> jsonParams;


    public searchPresenter(searchPresenterListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
    }



    public void searchNow(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        Log.i("dataaa",jsonParams+"");
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().searchList(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<places_result>() {
            @Override
            public void onResponse(Call<places_result> call, Response<places_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    places_result places_result = response.body();
                    mListener.onLoadPlacesSearchPlaces(places_result);
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    mListener.onLoadFailed(response.code(),response.message());

                }

            }

            @Override
            public void onFailure(Call<places_result> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }


}

