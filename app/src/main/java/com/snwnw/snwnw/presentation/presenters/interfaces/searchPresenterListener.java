package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.place_model;
import com.snwnw.snwnw.domain.models.places_result;

/**
 * Created by fifi elshafie on 7/1/2018.
 */

public interface searchPresenterListener {
    public void onLoadPlacesSearchPlaces(places_result places_result) ;
    public void onLoadFailed (int Code ,String msg) ;

}
