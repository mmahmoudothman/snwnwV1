package com.snwnw.snwnw.presentation.presenters.impl;

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

public class placesPresenter {

    Context context;
    private final placesPrestenerListener mListener;
    SNWNWServices snwnwServices;
    HashMap<String, Object> jsonParams;


    public placesPresenter(placesPrestenerListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
        this.snwnwServices = new SNWNWServices();
    }

    public void addPlace(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().addNewPlace(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<add_place_model>() {
            @Override
            public void onResponse(Call<add_place_model> call, Response<add_place_model> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    add_place_model user_data = response.body();
                    Log.i("iaamdata1",user_data.getMsg());
                  //  Toast.makeText(context,user_data.(),Toast.LENGTH_SHORT).show();
                    mListener.onPlaceUploadDone(true);
                   // mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    mListener.onPlaceUploadFailed(response.code(),response.message());
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
            public void onFailure(Call<add_place_model> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void searchPlaces(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().searchPlaces(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<places_result>() {
            @Override
            public void onResponse(Call<places_result> call, Response<places_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    places_result places_result = response.body();
                    mListener.onLoadPlacesSearchPlaces(places_result);
                    // mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                    if (response.code()== Constants.ExpiredToken){
                        mListener.onUpdateFail(response.code());
                        // mListener.expiredToken(response.code());
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
            public void onFailure(Call<places_result> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void addPlaceToFav(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().addPlaceToFav(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<fav_result>() {
            @Override
            public void onResponse(Call<fav_result> call, Response<fav_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    fav_result places_result = response.body();
                    mListener.onFavBtnClicked(places_result.getMsg());
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
            public void onFailure(Call<fav_result> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void myFavList(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().getMyFavList(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<places_result>() {
            @Override
            public void onResponse(Call<places_result> call, Response<places_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    places_result places_result = response.body();
                    mListener.onLoadPlacesSearchPlaces(places_result);
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
            public void onFailure(Call<places_result> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    public void myUploadedPlaces(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().getMyUploadedPlaces(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<places_result>() {
            @Override
            public void onResponse(Call<places_result> call, Response<places_result> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    places_result places_result = response.body();
                    mListener.onLoadPlacesSearchPlaces(places_result);
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
            public void onFailure(Call<places_result> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }


    public void placeDetails(HashMap<String, Object> jsonParams,String AuthorizedToken) {
        Log.i("authorizedtoken",AuthorizedToken);
        final KProgressHUD dialog =KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        snwnwServices.getAPI().placeDetails(jsonParams, "application/json",AuthorizedToken).enqueue(new Callback<place_details_model>() {
            @Override
            public void onResponse(Call<place_details_model> call, Response<place_details_model> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    place_details_model places_result = response.body();
                  //  mListener.onLoadPlacesSearchPlaces(places_result);
                    mListener.onPlaceDetailsLoaded(places_result.getPlace_model());
                    // mListener.userData(user_data);
                    //   Log.i("iamthetoken",user_data.getUser().getToken());
                    //    mListener.onRegisterSuccess(user_data.getUser().getToken());
                }
                else {
                    Log.i("iamerrroe","errror"+response.code()+response);
                   // mListener.onUpdateFail(response.code());
                    Log.i("iamerrroe","errror"+response.code()+response);
                    if (response.code()== Constants.ExpiredToken) {
                        mListener.onUpdateFail(response.code());
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
            public void onFailure(Call<place_details_model> call, Throwable t) {
                //  mListener.onRegistrFailed(t.getMessage());
                Log.i("iamerrroe","failed"+t.getMessage());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

}
