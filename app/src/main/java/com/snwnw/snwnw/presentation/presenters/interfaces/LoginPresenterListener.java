package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.user_model;

/**
 * Created by afaf.elshafey on 11/13/2017.
 */

public interface LoginPresenterListener {

    public void onRegisterSuccess(String Token);
    public void onRegistrFailed (String Error) ;
    public void onLoginSuccess(user_model user_model) ;
    public void onLoginFailed (String Error);
    public void onForgetPassSuccess(String Msg);
    public void onForgetPassFail(String Msg);

    public void onHomeCountriesLoadedSuccess(homeCountriesModel model) ;
    public void onHomeCountriesLoadedFailed(String error) ;

    public void onHomeCitiesLoadedSuccess(homeCitiesModel model) ;
    public void onHomeCitiesLoadedFailed(String error) ;

    //public void OnLoginSuccess(signup_result_model signup_result_model);
    //public void OnLoginFailed(String Error);
}
