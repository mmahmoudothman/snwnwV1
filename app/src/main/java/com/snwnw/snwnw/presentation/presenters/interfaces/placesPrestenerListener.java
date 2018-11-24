package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.service_cat_model;

import java.util.ArrayList;

/**
 * Created by fifi elshafie on 2/9/2018.
 */

public interface placesPrestenerListener {

    public void onPlaceUploadDone(Boolean Result) ;
    public void onPlaceUploadFailed(int code,String result) ;
    public void getAllCategories(ArrayList<service_cat_model>allservices);
    public void getAllSubCategories(ArrayList<service_cat_model>allservices);
    public void onLoadPlacesSearchPlaces(places_result places_result) ;
    public void onPlaceDetailsLoaded(place_model place_model) ;
    public void onUpdateFail (int Code) ;

    public void onFavBtnClicked(String msg) ;
}
