package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.favInterestModel;

/**
 * Created by fifi elshafie on 7/1/2018.
 */

public interface favPresenterListener {

    public void onFavsLoadSuccess(favInterestModel model) ;
    public void onFavsLoadedFailed(int code ,String error) ;

    public void onAddedDone (String msg) ;
    public void onAddedFailed(String msg) ;
}
